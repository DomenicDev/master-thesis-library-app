package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookCommand.AddBook
import de.cassisi.catalogue.book.BookCommand.UpdateBookSignature

class BookCommandHandler(private val repository: BookRepository) {

    fun handle(command: AddBook) {
        val book = BookFactory.create(command)
        this.repository.save(book)
    }

    fun handle(command: UpdateBookSignature) {
        val bookId = command.bookId

        // load book aggregate
        val book = this.repository.getById(bookId)
        book.execute(command)
        this.repository.save(book)
    }


}