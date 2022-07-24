package de.cassisi.lending.loan

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.AbstractEventStoreRepository
import de.cassisi.lending.common.Version
import de.cassisi.lending.student.StudentId

class LoanEventStoreRepository(client: EventStoreDBClient) : AbstractEventStoreRepository<Loan, LoanId, LoanEvent, SerializableLoanEvent>(client) {

    companion object {
        private const val LOAN_CREATED = "loan-created"
        private const val LOAN_EXTENDED = "loan-extended"
        private const val LOAN_CLOSED = "loan-closed"
    }

    override fun createEmptyAggregate(id: LoanId, version: Version): Loan {
        return LoanFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: LoanEvent): SerializableLoanEvent {
        return when (event) {
            is LoanCreated -> SerializableLoanCreated(
                event.bookId.id,
                event.studentId.uuid,
                event.bookId.id,
                event.startDate,
                event.endDate
            )
            is LoanExtended -> SerializableLoanExtended(
                event.loanId.uuid,
                event.startDate,
                event.endDate,
                event.extensions
            )
            is LoanClosed -> SerializableLoanClosed(
                event.loanId.uuid,
                event.returnDate
            )
        }
    }

    override fun convertToDomainEvent(raw: SerializableLoanEvent): LoanEvent {
        return when (raw) {
            is SerializableLoanCreated -> LoanCreated(
                LoanId(raw.loanId),
                StudentId(raw.studentId),
                BookId(raw.bookId),
                raw.startDate,
                raw.endDate
            )
            is SerializableLoanExtended -> LoanExtended(
                LoanId(raw.loanId),
                raw.startDate,
                raw.endDate,
                raw.extensions
            )
            is SerializableLoanClosed -> LoanClosed(
                LoanId(raw.loanId),
                raw.returnDate
            )
        }
    }

    override fun getSerializableEventType(eventType: String): Class<out SerializableLoanEvent> {
        return when (eventType) {
            LOAN_CREATED -> SerializableLoanCreated::class.java
            LOAN_EXTENDED -> SerializableLoanExtended::class.java
            LOAN_CLOSED -> SerializableLoanExtended::class.java
            else -> throw IllegalArgumentException()
        }
    }

    override fun getEventTypeName(event: LoanEvent): String {
        return when (event) {
            is LoanCreated -> LOAN_CREATED
            is LoanExtended -> LOAN_EXTENDED
            is LoanClosed -> LOAN_CLOSED
        }
    }

    override fun toStreamName(id: LoanId): String {
        return "loan-${id.uuid}"
    }
}