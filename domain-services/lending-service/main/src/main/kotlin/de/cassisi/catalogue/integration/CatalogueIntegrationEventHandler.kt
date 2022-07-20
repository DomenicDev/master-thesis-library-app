package de.cassisi.catalogue.integration

import de.cassisi.lending.book.BookCommand
import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.add.AddBook
import org.springframework.stereotype.Component

@Component
class CatalogueIntegrationEventHandler(private val addBook: AddBook) {


    fun handle(event: BookAddedToCatalogueSerializable) {
        val command = BookCommand.AddBook(BookId(event.bookId))
        addBook.execute(command)
    }

}