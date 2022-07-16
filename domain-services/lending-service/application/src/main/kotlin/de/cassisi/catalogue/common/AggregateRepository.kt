package de.cassisi.catalogue.common

interface AggregateRepository<ID, Aggregate> {

    fun save(aggregate: Aggregate)

    fun getById(id: ID): Aggregate

}