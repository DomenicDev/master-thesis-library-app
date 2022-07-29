package de.cassisi.cataloguesearchprojector.dbmodel


data class MetadataDocument(
    val metadataId: String,
    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,
)