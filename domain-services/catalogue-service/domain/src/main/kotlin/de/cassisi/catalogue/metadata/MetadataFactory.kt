package de.cassisi.catalogue.metadata

import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.common.Versions

object MetadataFactory {

    fun create(command: AddMetadataCommand): Metadata {
        val metadata = MetadataAggregate(command.metadataId, Versions.init())
        val event = MetadataAdded(
            command.metadataId,
            command.title,
            command.author,
            command.isbn,
            command.publisher
        )
        metadata.registerEvent(event)
        return metadata
    }

    fun empty(metadataId: MetadataId, version: Version): Metadata {
        return MetadataAggregate(metadataId, version)
    }

}