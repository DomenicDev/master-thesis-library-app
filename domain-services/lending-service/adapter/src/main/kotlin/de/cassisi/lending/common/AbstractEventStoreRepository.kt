package de.cassisi.lending.common

import com.eventstore.dbclient.*

abstract class AbstractEventStoreRepository<AggregateType : EventSourcedAggregate<ID, EventType>, ID, EventType, SerializedEventType>(
    private val client: EventStoreDBClient
) {

    fun saveAggregate(aggregate: AggregateType) {
        val options: AppendToStreamOptions = AppendToStreamOptions.get()

        // check version / revision match
        val currentVersion = aggregate.getVersion()
        if (currentVersion.isNew()) options.expectedRevision(ExpectedRevision.NO_STREAM)
        else options.expectedRevision(ExpectedRevision.expectedRevision(currentVersion.get()))

        // map events to serializable objects
        val changes = aggregate.getChanges()
        val eventDataIterator = changes.map { toSerializableEvent(it) }.iterator()

        // append to stream
        val streamName = toStreamName(aggregate.getId())
        client.appendToStream(streamName, options, eventDataIterator).get()
    }

    private fun toSerializableEvent(event: EventType): EventData {
        val serializableEvent = convertToSerializableEvent(event)
        val eventType = getEventTypeName(event)
        return GsonBuilder.buildEventData(eventType, serializableEvent)
    }

    fun loadAggregateById(id: ID): AggregateType {
        val options = ReadStreamOptions.get().fromStart().forwards()
        val streamName = toStreamName(id)
        val result = client.readStream(streamName, options).get()
        val domainEvents = result.events.map { toDomainEvent(it) }

        val version = result.events.last().event.streamRevision.valueUnsigned

        val aggregate = createEmptyAggregate(id, Versions.of(version))
        aggregate.loadFromHistory(domainEvents)
        return aggregate
    }

    private fun toDomainEvent(event: ResolvedEvent): EventType {
        val eventType = event.originalEvent.eventType
        val eventData = event.originalEvent.eventData
        val json = String(eventData, Charsets.UTF_8)

        val serializableType = getSerializableEventType(eventType)
        val raw = readJson(json, serializableType)
        return convertToDomainEvent(raw)
    }

    private fun <T> readJson(json: String, type: Class<T>): T {
        return GsonBuilder.gson().fromJson(json, type)
    }

    abstract fun createEmptyAggregate(id: ID, version: Version): AggregateType

    abstract fun convertToSerializableEvent(event: EventType): SerializedEventType

    abstract fun convertToDomainEvent(raw: SerializedEventType): EventType

    abstract fun getSerializableEventType(eventType: String): Class<out SerializedEventType>

    abstract fun getEventTypeName(event: EventType): String

    abstract fun toStreamName(id: ID): String
}