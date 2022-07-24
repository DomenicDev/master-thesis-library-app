package de.cassisi.lending.returnbook

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.loan.LoanCommand
import de.cassisi.lending.loan.LoanId
import java.time.LocalDate

class ReturnBookExecutor(private val repository: ReturnBookRepository) : ReturnBook {

    override fun execute(loanId: LoanId) {
        // get loan and book aggregate
        val loan = repository.getLoanById(loanId)
        val bookId = loan.getBookId()
        val book = repository.getBookById(bookId)

        // 1. Step: mark the book as available
        val returnCommand = BookCommand.ReturnBookCommand(bookId)
        book.execute(returnCommand)

        // 2. Step: Close the loan
        val closeCommand = LoanCommand.CloseLoan(loanId, LocalDate.now())
        loan.execute(closeCommand)
    }
}