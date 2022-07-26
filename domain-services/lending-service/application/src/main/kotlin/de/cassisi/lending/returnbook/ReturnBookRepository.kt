package de.cassisi.lending.returnbook

import de.cassisi.lending.book.Book
import de.cassisi.lending.book.BookId
import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface ReturnBookRepository {

    fun getLoanById(loanId: LoanId): Loan

    fun getBookById(bookId: BookId): Book

}