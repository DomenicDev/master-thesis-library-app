package de.cassisi.catalogue.common

abstract class Aggregate<ID, EventType>(val id: ID, val version: Version) {

    private val changes: MutableList<EventType> = mutableListOf()

     fun loadFromHistory(events: List<EventType>) {
         events.forEach { handleEvent(it) }
     }

    fun registerEvent(event: EventType) {
        changes.add(event) // store event in changes for later persistence
        handleEvent(event)      // handle event and update state
    }

    fun clearChanges() {
        this.changes.clear()
    }

    protected abstract fun handleEvent(event: EventType)

}