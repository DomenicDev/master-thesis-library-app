package de.cassisi.lending.lendbook

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.borrow.BorrowBook
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.loan.LoanCommand
import de.cassisi.lending.loan.LoanId
import de.cassisi.lending.loan.create.CreateLoan

class LendBookExecutor(
    private val borrowBook: BorrowBook,
    private val returnBook: ReturnBook,
    private val createLoan: CreateLoan,
    private val repository: LendBookRepository
) : LendBook {

    override fun execute(command: LoanCommand.OpenLoan): Result<LoanId> {
        val loanId = command.loanId
        val bookId = command.bookId
        val studentId = command.studentId
        val startDate = command.startDate

        // validate that specified book and student actually exist
        if (!repository.exists(bookId)) {
            return Result.failure(IllegalStateException("Book id ${bookId.id} does not exist"))
        }
        if (!repository.exists(studentId)) {
            return Result.failure(IllegalStateException("Student id ${studentId.uuid} does not exist"))
        }

        // 1) Borrow Book
        val borrowBookCommand = BookCommand.BorrowBookCommand(bookId)
        try {
            borrowBook.execute(borrowBookCommand)
        } catch (e : Exception) {
            return Result.failure(e)
        }

        // 2) create loan
        val createLoanCommand  = LoanCommand.OpenLoan(
            loanId, studentId, bookId, startDate
        )
        return try {
            createLoan.execute(createLoanCommand)
            Result.success(loanId)
        } catch (e: Exception) {
            // in case of failure, we have to return the book to retain a consistent state
            // This acts as a compensation action
            val returnBookCommand = BookCommand.ReturnBookCommand(bookId)
            returnBook.execute(returnBookCommand)
            Result.failure(e)
        }
    }

}