package de.cassisi.lending.book.add

import de.cassisi.lending.book.BaseBookRepository
import de.cassisi.lending.book.BookEventStoreRepository

class AddBookEventStoreRepository(repository: BookEventStoreRepository) : AddBookRepository, BaseBookRepository(repository)
