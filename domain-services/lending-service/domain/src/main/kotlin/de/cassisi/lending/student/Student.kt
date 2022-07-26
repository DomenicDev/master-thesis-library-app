package de.cassisi.lending.student

import de.cassisi.lending.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun isMatriculated(): Boolean

    fun getMatriculationStatus(): MatriculationStatus

    fun getCharges(): Charges

    fun execute(command: UpdateStudentChargesCommand)

    fun execute(command: UpdateMatriculationStatusCommand)

}