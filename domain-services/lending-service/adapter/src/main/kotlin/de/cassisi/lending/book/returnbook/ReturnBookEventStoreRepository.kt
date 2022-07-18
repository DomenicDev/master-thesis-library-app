package de.cassisi.lending.book.returnbook

import de.cassisi.lending.book.BaseBookRepository
import de.cassisi.lending.book.BookEventStoreRepository

class ReturnBookEventStoreRepository(repository: BookEventStoreRepository): ReturnBookRepository, BaseBookRepository(repository)