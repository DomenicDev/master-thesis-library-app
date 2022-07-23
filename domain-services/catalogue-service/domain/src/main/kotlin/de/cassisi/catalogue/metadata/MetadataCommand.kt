package de.cassisi.catalogue.metadata

interface MetadataCommand

data class AddMetadataCommand(
    val metadataId: MetadataId,
    val title: Title,
    val author: Author,
    val isbn: ISBN,
    val publisher: Publisher
): MetadataCommand
