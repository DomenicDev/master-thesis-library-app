package de.cassisi.lending.book.add

import de.cassisi.lending.book.Book
import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.AggregateRepository

interface AddBookRepository : AggregateRepository<BookId, Book> {
}