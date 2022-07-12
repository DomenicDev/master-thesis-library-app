package de.cassisi.catalogue.book

import java.util.*

data class AddBookRequest(
    val campusId: UUID,
    val metadataId: UUID,
    val signature: String
)
