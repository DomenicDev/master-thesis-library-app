package de.cassisi.catalogue.book.returnbook

import de.cassisi.catalogue.book.BookCommand

class ReturnBookExecutor(private val repository: ReturnBookRepository) : ReturnBook {

    override fun execute(command: BookCommand.ReturnBookCommand) {
        val bookId = command.bookId
        val book = repository.getById(bookId)
        book.execute(command)
        repository.save(book)
    }
}