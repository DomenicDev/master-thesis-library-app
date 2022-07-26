package de.cassisi.lending.book

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.add.AddBook
import de.cassisi.lending.book.borrow.BorrowBook
import de.cassisi.lending.book.extend.ExtendLoan
import de.cassisi.lending.book.extend.ExtendLoanCommand
import de.cassisi.lending.book.returnbook.ReturnBook
import de.cassisi.lending.student.StudentId
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookService(
    private val addBook: AddBook,
    private val borrowBook: BorrowBook,
    private val extendLoan: ExtendLoan,
    private val returnBook: ReturnBook
    ) {

    fun addBook(bookId: BookId) {
        // we add our own model of book
        val command = BookCommand.AddBook(bookId)
        addBook.execute(command)
    }

    fun borrowBook(bookId: BookId, studentId: StudentId, startDate: LocalDate) {
        val command = BookCommand.BorrowBookCommand(bookId, studentId, startDate)
        borrowBook.execute(command)
    }

    fun extendLoan(bookId: BookId) {
        val command = ExtendLoanCommand(bookId)
        extendLoan.execute(command)
    }

    fun returnBook(bookId: BookId, returnDate: LocalDate) {
        val command = BookCommand.ReturnBookCommand(bookId, returnDate)
        returnBook.execute(command)
    }

}