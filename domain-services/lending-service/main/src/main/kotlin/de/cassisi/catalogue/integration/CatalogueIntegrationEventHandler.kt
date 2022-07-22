package de.cassisi.catalogue.integration

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.add.AddBook
import org.springframework.stereotype.Component

@Component
class CatalogueIntegrationEventHandler(private val addBook: AddBook) {


    fun handleBookAdded(bookId: BookId) {
        // we add our own model of book
        val command = BookCommand.AddBook(bookId)
        addBook.execute(command)
    }

}