package de.cassisi.lending.common

interface AggregateRepository<ID, Aggregate> {

    fun save(aggregate: Aggregate)

    fun getById(id: ID): Aggregate

}