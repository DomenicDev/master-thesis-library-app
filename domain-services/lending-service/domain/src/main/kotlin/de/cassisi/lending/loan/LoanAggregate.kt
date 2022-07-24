package de.cassisi.lending.loan

import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

class LoanAggregate(id: LoanId, version: Version) : Loan, BaseAggregate<LoanId, LoanEvent>(id, version) {

    companion object {
        private const val NUMBER_OF_WEEKS_FOR_EXTENSION = 4L
    }

    private val state = LoanState()

    override fun handleEvent(event: LoanEvent) {
        when (event) {
            is LoanCreated -> state.handle(event)
            is LoanClosed -> state.handle(event)
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

    override fun execute(command: LoanCommand.CloseLoan) {
        // validate if loan was already returned
        if (getState().returned) {
            throw LoanAlreadyReturned(getId())
        }
        val returnDate = command.returnDate

        val event = LoanClosed(
            getId(),
            returnDate
        )
        registerEvent(event)
    }
}