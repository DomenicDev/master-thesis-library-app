package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookEvent.BookAddedToCatalogue
import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.common.Versions

object BookFactory {

    fun create(command: BookCommand.AddBook): Book {
        val bookId = command.bookId
        val metadataId = command.metadataId
        val campusId = command.campusId
        val signature = command.signature

        val book = BookAggregate(bookId, Versions.init())
        val event = BookAddedToCatalogue(bookId, metadataId, campusId, signature)
        book.registerEvent(event)
        return book
    }

    fun empty(bookId: BookId, version: Version): Book {
        return BookAggregate(bookId, version)
    }
}