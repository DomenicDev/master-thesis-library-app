package de.cassisi.lending.book

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.common.AbstractEventStoreRepository
import de.cassisi.lending.common.Version

class BookEventStoreRepository(client: EventStoreDBClient) :
    AbstractEventStoreRepository<BorrowableBook, BookId, BookEvent, SerializableBookEvent>(client) {

    companion object {
        private const val BOOK_ADDED = "book-added"
        private const val BOOK_BORROWED = "book-borrowed"
        private const val BOOK_RETURNED = "book-returned"
    }

    override fun createEmptyAggregate(id: BookId, version: Version): BorrowableBook {
        return BookFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: BookEvent): SerializableBookEvent {
        return when (event) {
            is BorrowableBookAdded -> SerializableBorrowableBookAdded(event.bookId.id)
            is BookBorrowed -> SerializableBookBorrowed(event.bookId.id)
            is BookReturned -> SerializableBookReturned(event.bookId.id)
        }
    }

    override fun convertToDomainEvent(raw: SerializableBookEvent): BookEvent {
        return when (raw) {
            is SerializableBorrowableBookAdded -> BorrowableBookAdded(BookId(raw.bookId))
            is SerializableBookBorrowed -> BookBorrowed(BookId(raw.bookId))
            is SerializableBookReturned -> BookReturned(BookId(raw.bookId))
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableBookEvent> {
        return when (eventType) {
            BOOK_ADDED -> SerializableBorrowableBookAdded::class.java
            BOOK_BORROWED -> SerializableBookBorrowed::class.java
            BOOK_RETURNED -> SerializableBookReturned::class.java
            else -> throw IllegalArgumentException("$eventType is not known")
        }
    }

    override fun toStreamName(id: BookId): String {
        return "borrowablebook-${id.id}"
    }

    override fun getEventTypeName(event: BookEvent): String {
        return when (event) {
            is BorrowableBookAdded -> BOOK_ADDED
            is BookBorrowed -> BOOK_BORROWED
            is BookReturned -> BOOK_RETURNED
        }
    }
}