package de.cassisi.lending.student

import de.cassisi.lending.common.EventSourcedAggregate

interface Student : EventSourcedAggregate<StudentId, StudentEvent> {

    fun isMatriculated(): Boolean

    fun getMatriculationStatus(): MatriculationStatus

    fun getLockStatus(): LockStatus

    fun isLocked(): Boolean

    fun changeMatriculationStatus(updatedStatus: MatriculationStatus)

    fun lockStudentForLending()

    fun unlockStudentForLending()

}