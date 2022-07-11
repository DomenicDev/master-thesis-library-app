package de.cassisi.catalogue.campus

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.common.AbstractEventStoreRepository
import de.cassisi.catalogue.common.Version

class CampusEventStoreRepository(client: EventStoreDBClient) : CampusRepository, AbstractEventStoreRepository<Campus, CampusId, CampusEvent>(client) {

    companion object {
        private const val CAMPUS_OPENED = "campus-opened"
    }

    override fun createEmptyAggregate(id: CampusId, version: Version): Campus {
        return CampusFactory.empty(id, version)
    }

    override fun convertToSerializableEvent(event: CampusEvent): Any {
        return when (event) {
            is CampusOpened -> CampusOpenedSerializable(
                event.campusId.campusId,
                event.campusLocation.campusLocation)
        }
    }

    override fun convertToDomainEvent(raw: Any): CampusEvent {
        return when (raw) {
            is CampusOpenedSerializable -> CampusOpened(
                CampusId(raw.campusId),
                CampusLocation(raw.campusLocation)
            )
            else -> throw IllegalStateException("No match found")
        }
    }

    override fun getSerializableEventType(eventType: String): Class<*> {
        return when (eventType) {
            CAMPUS_OPENED -> CampusOpenedSerializable::class.java
            else -> throw IllegalStateException("No serializable class found")
        }
    }

    override fun toStreamName(id: CampusId): String {
        return "campus-${id.campusId}"
    }

    override fun getEventTypeName(event: CampusEvent): String {
        return when (event) {
            is CampusOpened -> CAMPUS_OPENED
        }
    }

    override fun save(aggregate: Campus) {
        saveAggregate(aggregate)
    }

    override fun getById(id: CampusId): Campus {
        return loadAggregateById(id)
    }
}