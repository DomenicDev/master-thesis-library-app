package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BookEventStoreRepository
import de.cassisi.lending.book.BaseBookRepository

class BorrowBookEventStoreRepository(
    repository: BookEventStoreRepository
) : BorrowBookRepository, BaseBookRepository(repository)