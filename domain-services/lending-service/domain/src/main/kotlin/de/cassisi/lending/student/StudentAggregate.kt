package de.cassisi.lending.student

import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version

class StudentAggregate(id: StudentId, version: Version) : Student, BaseAggregate<StudentId, StudentEvent>(id, version) {

    private lateinit var status: MatriculationStatus
    private lateinit var lockStatus: LockStatus

    override fun isMatriculated(): Boolean {
        return getMatriculationStatus().status
    }

    override fun getMatriculationStatus(): MatriculationStatus {
        return this.status
    }

    override fun getLockStatus(): LockStatus {
        return this.lockStatus
    }

    override fun isLocked(): Boolean {
        return this.lockStatus.locked
    }

    override fun changeMatriculationStatus(updatedStatus: MatriculationStatus) {
        val event = StudentMatriculatedChanged(
            getId(),
            updatedStatus
        )
        registerEvent(event)
    }

    override fun lockStudentForLending() {
        if (isLocked()) return
        val event = StudentLockStatusChanged(getId(), LockStatus(true))
        registerEvent(event)
    }

    override fun unlockStudentForLending() {
        if (!isLocked()) return
        val event = StudentLockStatusChanged(getId(), LockStatus(false))
        registerEvent(event)
    }

    override fun handleEvent(event: StudentEvent) {
        when (event) {
            is StudentCreated -> handle(event)
            is StudentMatriculatedChanged -> handle(event)
            is StudentLockStatusChanged -> handle(event)
        }
    }

    private fun handle(event: StudentCreated) {
        this.status = event.matriculationStatus
        this.lockStatus = LockStatus(false)
    }

    private fun handle(event: StudentMatriculatedChanged) {
        this.status = event.matriculationStatus
    }

    private fun handle(event: StudentLockStatusChanged) {
        this.lockStatus = event.lockStatus
    }

}