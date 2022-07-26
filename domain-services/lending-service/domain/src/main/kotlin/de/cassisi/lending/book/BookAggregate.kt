package de.cassisi.lending.book

import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version
import de.cassisi.lending.student.StudentId
import java.time.LocalDate
import java.util.*

class BookAggregate(id: BookId, version: Version) : Book, BaseAggregate<BookId, BookEvent>(id, version) {

    private var currentLoan: Loan? = null

    override fun isAvailableForLoan(): Boolean {
        return this.currentLoan == null
    }

    override fun borrowBook(studentId: StudentId, startDate: LocalDate, policy: BorrowBookPolicy) {
        if (!isAvailableForLoan()) {
            throw BookAlreadyLoanException(getId())
        }
        // validate borrow policy
        policy.validateIfStudentIsAllowedToBorrow(studentId)

        // student is allowed to borrow the book
        val loanId = LoanId(UUID.randomUUID())
        val endDate = startDate.plusWeeks(6)

        val bookBorrowedEvent = BookBorrowed(
            getId(),
            loanId,
            studentId,
            startDate,
            endDate
        )
        registerEvent(bookBorrowedEvent)
    }

    override fun extendCurrentLoan(policy: ExtendLoanPolicy) {
        if (currentLoan == null) {
            throw IllegalStateException("Book is currently not borrowed.")
        }

        // validate extension quota
        if (currentLoan!!.maximumOfExtensionsReached()) {
            throw NoMoreExtensionsPossible(currentLoan!!.extensions)
        }

        // validate policy
        val studentId = currentLoan!!.studentId
        policy.validateIfStudentIsAllowedToExtendBook(studentId)

        // student is allowed to extend the loan
        val newEndDate = currentLoan!!.endDate.plusWeeks(4)
        val event = LoanExtended(
            getId(),
            currentLoan!!.loanId,
            studentId,
            currentLoan!!.startDate,
            newEndDate
        )
        registerEvent(event)
    }

    override fun returnBook(returnDate: LocalDate) {
        if (currentLoan == null) {
            throw IllegalStateException("Book is currently not loan")
        }

        val event = BookReturned(
            getId(),
            getCurrentLoan().loanId,
            returnDate
        )
        registerEvent(event)
    }

    /**
     * Only call when you are sure that currentLoan is not null
     */
    private fun getCurrentLoan(): Loan {
        return this.currentLoan!!
    }

    override fun handleEvent(event: BookEvent) {
        when (event) {
            is BookAdded -> handle(event)
            is BookBorrowed -> handle(event)
            is LoanExtended -> handle(event)
            is BookReturned -> handle(event)
        }
    }

    private fun handle(event: BookAdded) {
        this.currentLoan = null
    }

    private fun handle(event: BookBorrowed) {
        this.currentLoan = Loan(
            event.loanId,
            event.studentId,
            event.startDate,
            event.endDate,
            0
        )
    }

    private fun handle(event: LoanExtended) {
        getCurrentLoan().incrementExtension()
        getCurrentLoan().endDate = event.endDate
    }

    private fun handle(event: BookReturned) {
        this.currentLoan = null
    }
}