package de.cassisi.charging.student

import de.cassisi.charging.common.Version
import de.cassisi.charging.common.Versions

object StudentFactory {

    fun create(command: CreateChargingAccountCommand): Student {
        val aggregate = StudentAggregate(command.studentId, Versions.init())
        val createdEvent = StudentChargeAccountCreated(
            command.studentId,
            Charges(0)
        )
        aggregate.registerEvent(createdEvent)
        return aggregate
    }

    fun empty(studentId: StudentId, version: Version): Student {
        return StudentAggregate(studentId, version)
    }

}