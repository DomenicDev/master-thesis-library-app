package de.cassisi.catalogue.metadata

sealed interface MetadataEvent

data class MetadataAdded(
    val metadataId: MetadataId,
    val title: Title,
    val author: Author,
    val isbn: ISBN,
    val publisher: Publisher
): MetadataEvent