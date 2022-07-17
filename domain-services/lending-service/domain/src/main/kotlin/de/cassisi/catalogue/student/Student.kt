package de.cassisi.catalogue.student

import de.cassisi.catalogue.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun getMatriculationStatus(): MatriculationStatus

    fun getCharges(): Charges

    fun execute(command: ChargeStudent)

    fun execute(command: UpdateMatriculationStatus)

}