package de.cassisi.catalogue.book.add

import de.cassisi.catalogue.book.BookCommand
import de.cassisi.catalogue.book.BookFactory

class AddBookExecutor(private val repository: AddBookRepository) : AddBook {

    override fun execute(command: BookCommand.AddBook) {
        val bookId = command.bookId
        val book = BookFactory.new(command)
        repository.save(book)
    }
}