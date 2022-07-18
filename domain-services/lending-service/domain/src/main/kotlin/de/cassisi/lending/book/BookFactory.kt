package de.cassisi.lending.book

import de.cassisi.lending.common.Version
import de.cassisi.lending.common.Versions

object BookFactory {

    fun empty(bookId: BookId, version: Version): BorrowableBook {
        return BorrowableBookAggregate(bookId, version)
    }

    fun new(command: BookCommand.AddBook): BorrowableBook {
        val bookId = command.bookId
        val book = BorrowableBookAggregate(bookId, Versions.init())
        val event = BorrowableBookAdded(bookId)
        book.registerEvent(event)
        return book
    }
}