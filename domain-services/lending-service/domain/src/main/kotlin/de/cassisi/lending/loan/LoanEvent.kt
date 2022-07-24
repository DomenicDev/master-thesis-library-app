package de.cassisi.lending.loan

import de.cassisi.lending.book.BookId
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

sealed interface LoanEvent

data class LoanCreated(
    val loanId: LoanId,
    val studentId: StudentId,
    val bookId: BookId,
    val startDate: LocalDate,
    val endDate: LocalDate,
) : LoanEvent

data class LoanExtended(
    val loanId: LoanId,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val extensions: Int
) : LoanEvent

data class LoanClosed(
    val loanId: LoanId,
    val returnDate: LocalDate
) : LoanEvent