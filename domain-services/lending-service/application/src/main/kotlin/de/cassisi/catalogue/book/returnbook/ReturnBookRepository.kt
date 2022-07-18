package de.cassisi.catalogue.book.returnbook

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.book.BorrowableBook
import de.cassisi.catalogue.common.AggregateRepository

interface ReturnBookRepository : AggregateRepository<BookId, BorrowableBook>
