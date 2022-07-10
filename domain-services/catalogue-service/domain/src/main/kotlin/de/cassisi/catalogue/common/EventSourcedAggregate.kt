package de.cassisi.catalogue.common

interface EventSourcedAggregate<ID, EventType> : Aggregate<ID> {

    fun getChanges(): List<EventType>

}