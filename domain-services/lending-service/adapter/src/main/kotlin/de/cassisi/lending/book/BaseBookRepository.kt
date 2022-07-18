package de.cassisi.lending.book

import de.cassisi.lending.common.AggregateRepository

open class BaseBookRepository(private val repository: BookEventStoreRepository) : AggregateRepository<BookId, BorrowableBook> {

    override fun save(aggregate: BorrowableBook) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: BookId): BorrowableBook {
        return repository.loadAggregateById(id)
    }

}