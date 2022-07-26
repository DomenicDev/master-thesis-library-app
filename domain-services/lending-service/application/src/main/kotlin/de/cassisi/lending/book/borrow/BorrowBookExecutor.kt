package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BorrowBookPolicy
import de.cassisi.lending.book.BookCommand

class BorrowBookExecutor(private val repository: BorrowBookRepository, private val policy: BorrowBookPolicy) : BorrowBook {

    override fun execute(command: BookCommand.BorrowBookCommand) {
        val bookId = command.bookId
        val book = repository.getById(bookId)
        book.borrowBook(command.studentId, command.startDate, policy)
        repository.save(book)
    }
}