package de.cassisi.lending.book.reserve

import de.cassisi.lending.book.BaseBookRepository
import de.cassisi.lending.book.BookEventStoreRepository

class ReserveBookEventStoreRepository(repository: BookEventStoreRepository) : ReserveBookRepository, BaseBookRepository(repository)