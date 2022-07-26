package de.cassisi.lending.book.extend

import de.cassisi.lending.book.Book
import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.AggregateRepository

interface ExtendLoanRepository : AggregateRepository<BookId, Book>