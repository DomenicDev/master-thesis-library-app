package de.cassisi.catalogue.book.add

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.book.BorrowableBook
import de.cassisi.catalogue.common.AggregateRepository

interface AddBookRepository : AggregateRepository<BookId, BorrowableBook> {
}