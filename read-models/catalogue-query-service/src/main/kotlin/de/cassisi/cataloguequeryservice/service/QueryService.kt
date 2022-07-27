package de.cassisi.cataloguequeryservice.service

import de.cassisi.cataloguequeryservice.model.MetadataDocument
import de.cassisi.cataloguequeryservice.model.MetadataRepository
import org.springframework.stereotype.Service

@Service
class QueryService(private val metadataRepository: MetadataRepository) {

    fun getAll(): MutableList<MetadataDocument> {
        return metadataRepository.findAll()
    }

}