package de.cassisi.lending.book.extend

import de.cassisi.lending.book.BaseBookRepository
import de.cassisi.lending.book.BookEventStoreRepository

class ExtendLoanEventStoreRepository(repository: BookEventStoreRepository) : ExtendLoanRepository, BaseBookRepository(repository)