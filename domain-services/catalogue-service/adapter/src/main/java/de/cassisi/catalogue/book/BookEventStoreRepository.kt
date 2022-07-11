package de.cassisi.catalogue.book

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.book.BookEvent.BookAddedToCatalogue
import de.cassisi.catalogue.book.BookEvent.BookSignatureUpdated
import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.common.AbstractEventStoreRepository
import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.metadata.MetadataId

class BookEventStoreRepository(client: EventStoreDBClient) : BookRepository, AbstractEventStoreRepository<Book, BookId, BookEvent>(client) {

    companion object {
        private const val ET_BOOK_ADDED = "book-added"
        private const val ET_BOOK_SIGNATURE_UPDATED = "signature-updated"
    }

    override fun createEmptyAggregate(id: BookId, version: Version): Book {
        return BookFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: BookEvent): Any {
        when (event) {
            is BookAddedToCatalogue -> {
                return BookAddedToCatalogueSerializable(
                    event.bookId.id,
                    event.metadataId.id,
                    event.campusId.campusId,
                    event.signature.signature)
            }
            is BookSignatureUpdated -> {
                return BookSignatureUpdatedSerializable(
                    event.bookId.id,
                    event.campusId.campusId,
                    event.oldSignature.signature,
                    event.newSignature.signature
                )
            }
        }
    }

    override fun convertToDomainEvent(raw: Any): BookEvent {
        when (raw) {
            is BookAddedToCatalogueSerializable -> {
                return BookAddedToCatalogue(
                    BookId(raw.bookId),
                    MetadataId(raw.metadataId),
                    CampusId(raw.campusId),
                    Signature(raw.signature)
                )
            }
            is BookSignatureUpdatedSerializable -> {
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

    override fun getSerializableEventType(eventType: String): Class<*> {
        return when (eventType) {
            ET_BOOK_ADDED -> BookAddedToCatalogueSerializable::class.java
            ET_BOOK_SIGNATURE_UPDATED -> BookSignatureUpdatedSerializable::class.java
            else -> throw IllegalStateException("No matching class for event type $eventType")
        }
    }

    override fun getEventTypeName(event: BookEvent): String {
        return when (event) {
            is BookAddedToCatalogue -> ET_BOOK_ADDED
            is BookSignatureUpdated -> ET_BOOK_SIGNATURE_UPDATED
        }
    }

    override fun toStreamName(bookId: BookId): String {
        return "book-${bookId.id}"
    }

    override fun save(aggregate: Book) {
        saveAggregate(aggregate)
    }

    override fun getById(id: BookId): Book {
        return loadAggregateById(id)
    }

}