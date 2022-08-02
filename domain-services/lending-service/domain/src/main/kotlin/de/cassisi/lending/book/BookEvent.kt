package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId
import java.time.LocalDate


sealed interface BookEvent

data class BookAdded(
    val bookId: BookId
): BookEvent

data class BookBorrowed(
    val bookId: BookId,
    val loanId: LoanId,
    val studentId: StudentId,
    val startDate: LocalDate,
    val endDate: LocalDate
) : BookEvent

data class LoanExtended(
    val bookId: BookId,
    val loanId: LoanId,
    val studentId: StudentId,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val numberOfExtensions: Int
): BookEvent

data class BookReturned(
    val bookId: BookId,
    val loanId: LoanId,
    val returnDate: LocalDate
): BookEvent

data class BookReserved(
    val bookId: BookId,
    val reservedBy: StudentId,
    val reservationDate: LocalDate,
    val expirationDate: LocalDate
): BookEvent

data class ReservationCleared(
    val bookId: BookId
): BookEvent