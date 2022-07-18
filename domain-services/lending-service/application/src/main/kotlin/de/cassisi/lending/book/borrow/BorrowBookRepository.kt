package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.BorrowableBook
import de.cassisi.lending.common.AggregateRepository

interface BorrowBookRepository : AggregateRepository<BookId, BorrowableBook>