package de.cassisi.charging.student

import de.cassisi.charging.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun getCharges(): Charges

    fun execute(command: ChargeStudentCommand)

    fun execute(command: ClearChargesCommand)

}