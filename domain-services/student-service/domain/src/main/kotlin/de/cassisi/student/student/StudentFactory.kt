package de.cassisi.student.student

import de.cassisi.student.common.Version
import de.cassisi.student.common.Versions

object StudentFactory {

    fun create(command: CreateStudentCommand): Student {
        val aggregate = StudentAggregate(command.studentId, Versions.init())
        val createdEvent = StudentCreated(
            command.studentId,
            command.name,
            command.email,
            command.matriculationNumber,
            command.status
        )
        aggregate.registerEvent(createdEvent)
        return aggregate
    }

    fun empty(studentId: StudentId, version: Version): Student {
        return StudentAggregate(studentId, version)
    }

}