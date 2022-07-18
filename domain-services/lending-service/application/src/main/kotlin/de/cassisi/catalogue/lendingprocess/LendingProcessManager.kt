package de.cassisi.catalogue.lendingprocess

import de.cassisi.catalogue.book.BookCommand
import de.cassisi.catalogue.book.borrow.BorrowBook
import de.cassisi.catalogue.book.returnbook.ReturnBook
import de.cassisi.catalogue.loan.LoanCommand
import de.cassisi.catalogue.loan.create.CreateLoan

class LendingProcessManager(
    private val borrowBook: BorrowBook,
    private val returnBook: ReturnBook,
    private val createLoan: CreateLoan) {

    fun createLendingProcess(command: LoanCommand.OpenLoan) {
        val loanId = command.loanId
        val bookId = command.bookId
        val studentId = command.studentId
        val startDate = command.startDate

        // 1) Borrow Book
        val borrowBookCommand = BookCommand.BorrowBookCommand(bookId)
        try {
            borrowBook.execute(borrowBookCommand)
        } catch (e: Exception) {
            val returnBookCommand = BookCommand.ReturnBookCommand(bookId)
            returnBook.execute(returnBookCommand)
        }
        // TODO: Make error handling cleaner by avoiding try catch and use Either class or something similar

        // 2) create loan
        val createLoanCommand  = LoanCommand.OpenLoan(
            loanId, studentId, bookId, startDate
        )
        createLoan.execute(createLoanCommand)
    }

}