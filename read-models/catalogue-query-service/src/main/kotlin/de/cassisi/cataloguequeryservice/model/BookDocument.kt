package de.cassisi.cataloguequeryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BookDocument(
    @Id
    val bookId: String,
    val campusId: String,
    val location: String,
    val metadataId: String,
    val signature: String,
)