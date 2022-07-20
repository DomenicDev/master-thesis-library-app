package de.cassisi.student.student

import de.cassisi.student.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun getName(): Name

    fun getMatriculationNumber(): MatriculationNumber

    fun getEmail(): Email

    fun getMatriculationStatus(): MatriculationStatus

    fun execute(command: UpdateMatriculationStatusCommand)

}