package de.cassisi.catalogue.common

abstract class BaseAggregate<ID, EventType>(private val id: ID, private val version: Version): EventSourcedAggregate<ID, EventType> {

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

    override fun getId(): ID {
        return this.id
    }

    override fun getVersion(): Version {
        return this.version
    }

    override fun getChanges(): List<EventType> {
        return this.changes.toList()
    }

    protected abstract fun handleEvent(event: EventType)

}