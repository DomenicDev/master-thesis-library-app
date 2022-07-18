package de.cassisi.lending.loan

import de.cassisi.lending.book.BookId
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

sealed interface LoanCommand {

    data class OpenLoan(
        val loanId: LoanId,
        val studentId: StudentId,
        val bookId: BookId,
        val startDate: LocalDate
    ) : LoanCommand


    data class ExtendLoan(
        val loanId: LoanId
    )

    data class ReturnBook(
        val loanId: LoanId,
        val returnDate: LocalDate
    )


}