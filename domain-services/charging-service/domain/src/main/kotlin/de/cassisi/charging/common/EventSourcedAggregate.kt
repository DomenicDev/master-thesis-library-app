package de.cassisi.charging.common

interface EventSourcedAggregate<ID, EventType> : Aggregate<ID> {

    fun loadFromHistory(events: List<EventType>)

    fun getChanges(): List<EventType>

}