package de.cassisi.catalogue.book.borrow

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.book.BorrowableBook
import de.cassisi.catalogue.common.AggregateRepository

interface BorrowBookRepository : AggregateRepository<BookId, BorrowableBook>