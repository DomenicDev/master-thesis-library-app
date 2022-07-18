package de.cassisi.lending.book.add

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.BookFactory

class AddBookExecutor(private val repository: AddBookRepository) : AddBook {

    override fun execute(command: BookCommand.AddBook) {
        val bookId = command.bookId
        val book = BookFactory.new(command)
        repository.save(book)
    }
}