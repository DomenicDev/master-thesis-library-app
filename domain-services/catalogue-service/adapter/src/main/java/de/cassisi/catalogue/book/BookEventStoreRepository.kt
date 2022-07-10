package de.cassisi.catalogue.book

import com.eventstore.dbclient.AppendToStreamOptions
import com.eventstore.dbclient.EventData
import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.ExpectedRevision
import de.cassisi.catalogue.utils.GsonBuilder

class BookEventStoreRepository(private val client: EventStoreDBClient) : BookRepository {

    companion object {
        private const val ET_BOOK_ADDED = "book-added"
        private const val ET_BOOK_SIGNATURE_UPDATED = "signature-updated"
        private const val STREAM_NAME_PREFIX = "book"
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
        val uuid = aggregate.getId().id
        val streamName = "$STREAM_NAME_PREFIX-$uuid"
        client.appendToStream(streamName, options, eventDataIterator).get()
    }



    override fun getById(id: BookId): Book {
        TODO("Not yet implemented")
    }

    private fun toSerializableEvent(event: BookEvent): EventData {
        when (event) {
            is BookEvent.BookAddedToCatalogue -> {
                val data = BookAddedToCatalogueSerializable(
                    event.bookId.id,
                    event.metadataId.id,
                    event.campusId.campusId,
                    event.signature.signature)
                return GsonBuilder.buildEventData(ET_BOOK_ADDED, data)
            }
            is BookEvent.BookSignatureUpdated -> {
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

}