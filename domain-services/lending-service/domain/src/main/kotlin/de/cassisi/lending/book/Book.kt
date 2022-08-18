package de.cassisi.lending.book

import de.cassisi.lending.common.EventSourcedAggregate
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

sealed interface Book: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean

    fun borrowBook(studentId: StudentId, startDate: LocalDate, policy: BorrowBookPolicy): Result<Unit>

    fun extendCurrentLoan(policy: ExtendLoanPolicy): Result<Unit>

    fun returnBook(returnDate: LocalDate)

    fun reserveBook(studentId: StudentId, reservationDate: LocalDate): Result<Unit>

    fun clearReservation(): Result<Unit>

}