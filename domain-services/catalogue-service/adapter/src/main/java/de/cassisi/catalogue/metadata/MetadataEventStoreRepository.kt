package de.cassisi.catalogue.metadata

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.common.AbstractEventStoreRepository
import de.cassisi.catalogue.common.Version

class MetadataEventStoreRepository(client: EventStoreDBClient) : AbstractEventStoreRepository<Metadata, MetadataId, MetadataEvent>(client) {

    companion object {
        private const val ET_METADATA_ADDED = "metadata-added"
    }

    override fun createEmptyAggregate(id: MetadataId, version: Version): Metadata {
        return MetadataFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: MetadataEvent): Any {
        return when (event) {
            is MetadataAdded -> {
               SerializableMetadataAdded(
                   event.metadataId.uuid,
                   event.title.title,
                   event.author.fullName,
                   event.isbn.isbn,
                   event.publisher.name
               )
            }
        }
    }

    override fun convertToDomainEvent(raw: Any): MetadataEvent {
        return when (raw) {
            is SerializableMetadataAdded -> {
                MetadataAdded(
                    MetadataId(raw.metadataId),
                    Title(raw.title),
                    Author(raw.author),
                    ISBN(raw.isbn),
                    Publisher(raw.publisher)
                )
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getSerializableEventType(eventType: String): Class<*> {
        return when (eventType) {
            ET_METADATA_ADDED -> SerializableMetadataAdded::class.java
            else -> throw IllegalArgumentException()
        }
    }

    override fun getEventTypeName(event: MetadataEvent): String {
        return when (event) {
            is MetadataAdded -> ET_METADATA_ADDED
        }
    }

    override fun toStreamName(id: MetadataId): String {
        return "metadata-${id.uuid}"
    }
}