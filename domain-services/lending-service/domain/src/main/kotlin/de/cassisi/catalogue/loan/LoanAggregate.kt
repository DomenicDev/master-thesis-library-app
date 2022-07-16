package de.cassisi.catalogue.loan

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.common.BaseAggregate
import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.student.StudentId
import java.time.LocalDate

class LoanAggregate(id: LoanId, version: Version) : Loan, BaseAggregate<LoanId, LoanEvent>(id, version) {

    companion object {
        private const val NUMBER_OF_WEEKS_FOR_EXTENSION = 4L
    }

    private val state = LoanState()

    override fun handleEvent(event: LoanEvent) {
        when (event) {
            is LoanCreated -> state.handle(event)
            is LoanReturned -> state.handle(event)
            is LoanExtended -> state.handle(event)
        }
    }

    private fun getState() = this.state

    override fun getReturnDate(): LocalDate {
        return getState().returnDate
    }

    override fun getStudentId(): StudentId {
        return getState().studentId
    }

    override fun getBookId(): BookId {
        return getState().bookId
    }

    override fun getStartDate(): LocalDate {
        return getState().startDate
    }

    override fun getEndDate(): LocalDate {
        return getState().endDate
    }

    override fun getExtensions(): Int {
        return getState().numberOfExtensions
    }

    override fun isActive(): Boolean {
        return getState().active
    }

    override fun isReturned(): Boolean {
        return getState().returned
    }

    override fun execute(command: LoanCommand.ExtendLoan) {
        // validate if extension is even allowed
        if (getState().numberOfExtensions >= 3) {
            throw LoanExtensionExceeded(getId())
        }

        // calculate new end date
        val newExtensions = getState().numberOfExtensions++
        val newEndDate = getState().endDate.plusWeeks(NUMBER_OF_WEEKS_FOR_EXTENSION)

        // create and publish event
        val event = LoanExtended(
            getId(),
            getStartDate(),
            newEndDate,
            newExtensions
        )
        registerEvent(event)
    }

    override fun execute(command: LoanCommand.ReturnBook) {
        // validate if loan was already returned
        if (getState().returned) {
            throw LoanAlreadyReturned(getId())
        }
        val returnDate = command.returnDate

        val event = LoanReturned(
            getId(),
            returnDate
        )
        registerEvent(event)
    }
}