package de.cassisi.apigateway.service

import java.util.*

data class MetadataDocument(
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

data class StudentDTO(
    val studentId: UUID,
    val matriculationNumber: Int,
    val forename: String,
    val lastname: String,
    val email: String,
    val matriculated: Boolean,
    val charges: Int
)