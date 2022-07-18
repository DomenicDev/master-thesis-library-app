package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BookCommand

class BorrowBookExecutor(private val repository: BorrowBookRepository) : BorrowBook {

    override fun execute(command: BookCommand.BorrowBookCommand) {
        val bookId = command.bookId
        val book = repository.getById(bookId)
        book.execute(command)
        repository.save(book)
    }
}