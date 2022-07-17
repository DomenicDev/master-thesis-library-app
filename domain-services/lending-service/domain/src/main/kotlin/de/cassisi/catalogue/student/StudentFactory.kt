package de.cassisi.catalogue.student

import de.cassisi.catalogue.common.Versions

object StudentFactory {

    fun create(command: CreateStudent): Student {
        val aggregate = StudentAggregate(command.studentId, Versions.init())
        val createdEvent = StudentCreated(
            command.studentId,
            command.status,
            command.charges
        )
        aggregate.registerEvent(createdEvent)
        return aggregate
    }

    fun empty(studentId: StudentId): Student {
        return StudentAggregate(studentId, Versions.init())
    }

}