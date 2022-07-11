package de.cassisi.catalogue.book

import com.eventstore.dbclient.*
import de.cassisi.catalogue.book.BookEvent.BookAddedToCatalogue
import de.cassisi.catalogue.book.BookEvent.BookSignatureUpdated
import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.common.GsonBuilder
import de.cassisi.catalogue.common.Versions
import de.cassisi.catalogue.metadata.MetadataId

class BookEventStoreRepository(private val client: EventStoreDBClient) : BookRepository {

    companion object {
        private const val ET_BOOK_ADDED = "book-added"
        private const val ET_BOOK_SIGNATURE_UPDATED = "signature-updated"
    }

    override fun save(aggregate: Book) {
        val options: AppendToStreamOptions = AppendToStreamOptions.get()

        // check version / revision match
        val currentVersion = aggregate.getVersion()
        if (currentVersion.isNew()) options.expectedRevision(ExpectedRevision.NO_STREAM)
        else options.expectedRevision(ExpectedRevision.expectedRevision(currentVersion.get()))

        // map events to serializable objects
        val changes = aggregate.getChanges()
        val eventDataIterator = changes.map { toSerializableEvent(it) }.iterator()

        // append to stream
        val streamName = toStreamName(aggregate.getId())
        client.appendToStream(streamName, options, eventDataIterator).get()
    }


    override fun getById(id: BookId): Book {
        val options = ReadStreamOptions.get().fromStart().forwards()
        val streamName = toStreamName(id)
        val result = client.readStream(streamName, options).get()
        val domainEvents = result.events.map { toDomainEvent(it) }

        val version = result.events.last().event.streamRevision.valueUnsigned
        val aggregate = BookFactory.empty(id, Versions.of(version))
        aggregate.loadFromHistory(domainEvents)
        return aggregate
    }

    private fun toStreamName(bookId: BookId): String {
        val uuid = bookId.id.toString()
        return "book-$uuid"
    }

    private fun toSerializableEvent(event: BookEvent): EventData {
        when (event) {
            is BookAddedToCatalogue -> {
                val data = BookAddedToCatalogueSerializable(
                    event.bookId.id,
                    event.metadataId.id,
                    event.campusId.campusId,
                    event.signature.signature)
                return GsonBuilder.buildEventData(ET_BOOK_ADDED, data)
            }
            is BookSignatureUpdated -> {
                val data = BookSignatureUpdatedSerializable(
                    event.bookId.id,
                    event.campusId.campusId,
                    event.oldSignature.signature,
                    event.newSignature.signature
                )
                return GsonBuilder.buildEventData(ET_BOOK_SIGNATURE_UPDATED, data)
            }
        }
    }

    private fun toDomainEvent(event: ResolvedEvent): BookEvent {
        val eventType = event.originalEvent.eventType
        val eventData = event.originalEvent.eventData
        val json = String(eventData, Charsets.UTF_8)

        when (eventType) {
            ET_BOOK_ADDED -> {
                val raw = readJson<BookAddedToCatalogueSerializable>(json)
                return BookAddedToCatalogue(
                    BookId(raw.bookId),
                    MetadataId(raw.metadataId),
                    CampusId(raw.campusId),
                    Signature(raw.signature)
                )
            }
            ET_BOOK_SIGNATURE_UPDATED -> {
                val raw = readJson<BookSignatureUpdatedSerializable>(json)
                return BookSignatureUpdated(
                    BookId(raw.bookId),
                    CampusId(raw.campusId),
                    Signature(raw.oldSignature),
                    Signature(raw.newSignature)
                )
            }
            else -> throw IllegalStateException("Raw event data could not be mapped to domain event.")
        }
    }

    private inline fun <reified T> readJson(json: String, ): T {
        return GsonBuilder.gson().fromJson(json, T::class.java)
    }

}