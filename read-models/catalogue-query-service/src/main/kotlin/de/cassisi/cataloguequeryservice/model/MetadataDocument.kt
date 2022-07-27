package de.cassisi.cataloguequeryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "books")
data class MetadataDocument(
    @Id
    val metadataId: String,

    val title: String,
    val author: String,
    val isbn: String,
    val publisher: String,

    val copies: List<BookCopy>
)

data class BookCopy(
    val bookId: String,
    val campusId: String,
    val location: String,
    val signature: String
)