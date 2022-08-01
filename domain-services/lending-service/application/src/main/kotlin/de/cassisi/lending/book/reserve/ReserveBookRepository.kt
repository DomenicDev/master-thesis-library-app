package de.cassisi.lending.book.reserve

import de.cassisi.lending.book.Book
import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.AggregateRepository

interface ReserveBookRepository : AggregateRepository<BookId, Book>