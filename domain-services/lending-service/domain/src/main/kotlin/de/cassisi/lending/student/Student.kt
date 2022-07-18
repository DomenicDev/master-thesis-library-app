package de.cassisi.lending.student

import de.cassisi.lending.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun getMatriculationStatus(): MatriculationStatus

    fun getCharges(): Charges

    fun execute(command: ChargeStudentCommand)

    fun execute(command: UpdateMatriculationStatusCommand)

    fun execute(command: ResetChargesCommand)

}