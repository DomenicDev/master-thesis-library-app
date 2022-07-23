package de.cassisi.catalogue.metadata

import de.cassisi.catalogue.common.AggregateRepository

open class BaseMetadataEventStoreRepository(private val repository: MetadataEventStoreRepository) : AggregateRepository<MetadataId, Metadata> {

    override fun save(aggregate: Metadata) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: MetadataId): Metadata {
        return repository.loadAggregateById(id)
    }

}