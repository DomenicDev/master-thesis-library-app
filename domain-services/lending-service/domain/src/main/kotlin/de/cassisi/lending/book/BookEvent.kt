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
    val endDate: LocalDate
): BookEvent

data class BookReturned(
    val bookId: BookId,
    val loanId: LoanId,
    val returnDate: LocalDate
): BookEvent