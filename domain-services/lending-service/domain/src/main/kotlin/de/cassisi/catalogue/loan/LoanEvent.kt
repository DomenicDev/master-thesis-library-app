package de.cassisi.catalogue.loan

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.student.StudentId
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

data class LoanReturned(
    val loanId: LoanId,
    val returnDate: LocalDate
) : LoanEvent