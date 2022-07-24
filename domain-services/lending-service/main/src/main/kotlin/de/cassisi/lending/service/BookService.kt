package de.cassisi.lending.service

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.add.AddBook
import org.springframework.stereotype.Service

@Service
class BookService(private val addBook: AddBook) {

    fun addBook(bookId: BookId) {
        // we add our own model of book
        val command = BookCommand.AddBook(bookId)
        addBook.execute(command)
    }

}