package de.cassisi.lending.book

import de.cassisi.lending.common.AggregateRepository

open class BaseBookRepository(private val repository: BookEventStoreRepository) : AggregateRepository<BookId, Book> {

    override fun save(aggregate: Book) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: BookId): Book {
        return repository.loadAggregateById(id)
    }

}