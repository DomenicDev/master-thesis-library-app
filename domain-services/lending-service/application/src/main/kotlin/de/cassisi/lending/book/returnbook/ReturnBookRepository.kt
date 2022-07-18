package de.cassisi.lending.book.returnbook

import de.cassisi.lending.book.BookId
import de.cassisi.lending.book.BorrowableBook
import de.cassisi.lending.common.AggregateRepository

interface ReturnBookRepository : AggregateRepository<BookId, BorrowableBook>
