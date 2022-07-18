package de.cassisi.lending.student

import de.cassisi.lending.common.Version
import de.cassisi.lending.common.Versions

object StudentFactory {

    fun create(command: CreateStudentCommand): Student {
        val aggregate = StudentAggregate(command.studentId, Versions.init())
        val createdEvent = StudentCreated(
            command.studentId,
            command.status,
            command.charges
        )
        aggregate.registerEvent(createdEvent)
        return aggregate
    }

    fun empty(studentId: StudentId, version: Version): Student {
        return StudentAggregate(studentId, version)
    }

}