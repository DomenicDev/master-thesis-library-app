package de.cassisi.lending.book

import de.cassisi.lending.common.Version
import de.cassisi.lending.common.Versions

object BookFactory {

    fun empty(bookId: BookId, version: Version): Book {
        return BookAggregate(bookId, version)
    }

    fun new(command: BookCommand.AddBook): Book {
        val bookId = command.bookId
        val book = BookAggregate(bookId, Versions.init())
        val event = BorrowableBookAdded(bookId)
        book.registerEvent(event)
        return book
    }
}