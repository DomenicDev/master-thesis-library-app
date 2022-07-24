package de.cassisi.lending.service

import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.lendbook.LendBook
import de.cassisi.lending.loan.LoanCommand
import de.cassisi.lending.loan.LoanId
import de.cassisi.lending.loan.extend.ExtendLoan
import de.cassisi.lending.student.StudentId
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LoanService(
    private val lendBook: LendBook,
    private val extendLoan: ExtendLoan,
    private val returnBook: ReturnBook
    ) {

    fun borrowBook(loanId: LoanId, studentId: StudentId, bookId: BookId, startDate: LocalDate): Result<LoanId> {
        val command = LoanCommand.OpenLoan(loanId, studentId, bookId, startDate)
        return lendBook.execute(command)
    }

    fun extendLoan(loanId: LoanId): Result<LoanId> {
        val command = LoanCommand.ExtendLoan(loanId)
        return runCatching { extendLoan.execute(command) }.map { loanId }
    }

    fun returnBook(loanId: LoanId): Result<LoanId> {
        TODO("...")
    }

}