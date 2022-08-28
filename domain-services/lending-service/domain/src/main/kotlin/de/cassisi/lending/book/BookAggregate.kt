package de.cassisi.lending.book

import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version
import de.cassisi.lending.student.StudentId
import java.time.LocalDate
import java.util.*

class BookAggregate(id: BookId, version: Version) : Book, BaseAggregate<BookId, BookEvent>(id, version) {

    private var currentLoan: Loan = NoLoan
    private var reservation: Reservation = NoReservation

    override fun isAvailableForLoan(): Boolean {
        return this.currentLoan is NoLoan
    }

    override fun borrowBook(studentId: StudentId, startDate: LocalDate, policy: BorrowBookPolicy): Result<Unit> {
        if (!isAvailableForLoan()) {
            return Result.failure(BookAlreadyLoan(getId()))
        }

        if (reservation is ActiveReservation && (reservation as ActiveReservation).studentId != studentId) {
            return Result.failure(BookReservedBySomeoneElse())
        }

        // validate borrow policy
        val result = policy.validateIfStudentIsAllowedToBorrow(studentId)
        if (result.isFailure) return result

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

        // clear reservation if book was reserved
        if (reservation is ActiveReservation) {
            clearReservation()
        }
        return Result.success(Unit)
    }

    override fun extendCurrentLoan(policy: ExtendLoanPolicy): Result<Unit> {
        if (currentLoan is NoLoan) {
            return Result.failure(IllegalStateException("Book is currently not borrowed."))
        }

        // validate that there is no reservation for this book
        if (reservation is ActiveReservation) {
            return Result.failure(IllegalStateException("Book is reserved and therefore the loan cannot be extended."))
        }

        // validate extension quota
        val currentLoan = (this.currentLoan as ActiveLoan)
        if (currentLoan.maximumOfExtensionsReached()) {
            return Result.failure(NoMoreExtensionsPossible(currentLoan.extensions))
        }

        // validate policy
        val studentId = currentLoan.studentId
        val result = policy.validateIfStudentIsAllowedToExtendBook(studentId)
        if (result.isFailure) return result

        // student is allowed to extend the loan
        val newEndDate = currentLoan.endDate.plusWeeks(4)
        val event = LoanExtended(
            getId(),
            currentLoan.loanId,
            studentId,
            currentLoan.startDate,
            newEndDate,
            currentLoan.extensions+1
        )
        registerEvent(event)
        return Result.success(Unit)
    }

    override fun returnBook(returnDate: LocalDate): Result<Unit> {
        if (isAvailableForLoan()) {
            return Result.failure(IllegalStateException("Book is currently not loan"))
        }

        val event = BookReturned(
            getId(),
            getCurrentLoan().loanId,
            returnDate
        )
        registerEvent(event)
        return Result.success(Unit)
    }

    override fun reserveBook(studentId: StudentId, reservationDate: LocalDate): Result<Unit> {
        if (isAvailableForLoan()) {
            return Result.failure(BookNotLent(getId()))
        }
        if (reservation is ActiveReservation) {
            return Result.failure(BookAlreadyReserved(getId()))
        }

        val expirationDate = calculateExpirationDateForReservation()

        val event = BookReserved(
            getId(),
            studentId,
            reservationDate,
            expirationDate
        )

        registerEvent(event)
        return Result.success(Unit)
    }

    private fun calculateExpirationDateForReservation(): LocalDate {
        val loanEndDate = getCurrentLoan().endDate
        return loanEndDate.plusWeeks(1)
    }

    override fun clearReservation(): Result<Unit> {
        return if (reservation is ActiveReservation) {
            val event = ReservationCleared(getId())
            registerEvent(event)
            Result.success(Unit)
        } else {
            Result.failure(BookNotReserved())
        }
    }

    /**
     * Only call when you are sure that currentLoan is not null
     */
    private fun getCurrentLoan(): ActiveLoan {
        return this.currentLoan as ActiveLoan
    }

    override fun handleEvent(event: BookEvent) {
        when (event) {
            is BookAdded -> handle(event)
            is BookBorrowed -> handle(event)
            is LoanExtended -> handle(event)
            is BookReturned -> handle(event)
            is BookReserved -> handle(event)
            is ReservationCleared -> handle(event)
        }
    }

    private fun handle(event: BookAdded) {
        this.currentLoan = NoLoan
        this.reservation = NoReservation
    }

    private fun handle(event: BookBorrowed) {
        this.currentLoan = ActiveLoan(
            event.loanId,
            event.studentId,
            event.startDate,
            event.endDate,
            0
        )
    }

    private fun handle(event: LoanExtended) {
        getCurrentLoan().extensions = event.numberOfExtensions
        getCurrentLoan().endDate = event.endDate
    }

    private fun handle(event: BookReturned) {
        this.currentLoan = NoLoan
    }

    private fun handle(event: BookReserved) {
        this.reservation = ActiveReservation(
            event.reservedBy,
            event.reservationDate,
            event.expirationDate)
    }

    private fun handle(event: ReservationCleared) {
        this.reservation = NoReservation
    }
}