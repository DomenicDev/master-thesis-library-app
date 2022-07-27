package de.cassisi.cataloguequeryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CampusDocument(
    @Id
    val campusId: String,
    val location: String
)