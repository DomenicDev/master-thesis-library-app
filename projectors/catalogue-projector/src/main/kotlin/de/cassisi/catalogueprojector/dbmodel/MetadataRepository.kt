package de.cassisi.catalogueprojector.dbmodel

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MetadataRepository : MongoRepository<MetadataDocument, String>