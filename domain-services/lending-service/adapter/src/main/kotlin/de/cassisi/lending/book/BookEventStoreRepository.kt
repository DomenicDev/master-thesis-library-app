package de.cassisi.lending.book

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.common.AbstractEventStoreRepository
import de.cassisi.lending.common.Version
import de.cassisi.lending.student.StudentId

class BookEventStoreRepository(client: EventStoreDBClient) :
    AbstractEventStoreRepository<Book, BookId, BookEvent, SerializableBookEvent>(client) {

    companion object {
        private const val BOOK_ADDED = "book-added"
        private const val BOOK_BORROWED = "book-borrowed"
        private const val BOOK_RETURNED = "book-returned"
        private const val LOAN_EXTENDED = "loan-extended"
    }

    override fun createEmptyAggregate(id: BookId, version: Version): Book {
        return BookFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: BookEvent): SerializableBookEvent {
        return when (event) {
            is BookAdded -> SerializableBookAdded(event.bookId.id)
            is BookBorrowed -> SerializableBookBorrowed(
                event.bookId.id,
                event.loanId.uuid,
                event.studentId.uuid,
                event.startDate,
                event.endDate
            )
            is LoanExtended -> SerializableLoanExtended(
                event.bookId.id,
                event.loanId.uuid,
                event.studentId.uuid,
                event.startDate,
                event.endDate
            )
            is BookReturned -> SerializableBookReturned(
                event.bookId.id,
                event.loanId.uuid,
                event.returnDate
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableBookEvent): BookEvent {
        return when (raw) {
            is SerializableBookAdded -> BookAdded(
                BookId(raw.bookId)
            )
            is SerializableBookBorrowed -> BookBorrowed(
                BookId(raw.bookId),
                LoanId(raw.loanId),
                StudentId(raw.studentId),
                raw.startDate,
                raw.endDate
            )
            is SerializableBookReturned -> BookReturned(
                BookId(raw.bookId),
                LoanId(raw.loanId),
                raw.returnDate
            )
            is SerializableLoanExtended -> LoanExtended(
                BookId(raw.bookId),
                LoanId(raw.loanId),
                StudentId(raw.studentId),
                raw.startDate,
                raw.endDate
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableBookEvent> {
        return when (eventType) {
            BOOK_ADDED -> SerializableBookAdded::class.java
            BOOK_BORROWED -> SerializableBookBorrowed::class.java
            BOOK_RETURNED -> SerializableBookReturned::class.java
            LOAN_EXTENDED -> SerializableLoanExtended::class.java
            else -> throw IllegalArgumentException("$eventType is not known")
        }
    }

    override fun toStreamName(id: BookId): String {
        return "book-${id.id}"
    }

    override fun getEventTypeName(event: BookEvent): String {
        return when (event) {
            is BookAdded -> BOOK_ADDED
            is BookBorrowed -> BOOK_BORROWED
            is BookReturned -> BOOK_RETURNED
            is LoanExtended -> LOAN_EXTENDED
        }
    }
}