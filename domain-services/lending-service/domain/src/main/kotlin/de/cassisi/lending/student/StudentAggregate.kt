package de.cassisi.lending.student

import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version

class StudentAggregate(id: StudentId, version: Version) : Student, BaseAggregate<StudentId, StudentEvent>(id, version) {

    private lateinit var status: MatriculationStatus
    private lateinit var charges: Charges

    override fun isMatriculated(): Boolean {
        return getMatriculationStatus().status
    }

    override fun getMatriculationStatus(): MatriculationStatus {
        return this.status
    }

    override fun getCharges(): Charges {
        return this.charges
    }

    override fun execute(command: UpdateStudentChargesCommand) {
        val newCharges = command.currentCharges
        val event = StudentChargesChanged(
            getId(),
            newCharges
        )
        registerEvent(event)
    }

    override fun execute(command: UpdateMatriculationStatusCommand) {
        val event = StudentMatriculatedChanged(
            getId(),
            command.newStatus
        )
        registerEvent(event)
    }

    override fun handleEvent(event: StudentEvent) {
        when (event) {
            is StudentCreated -> handle(event)
            is StudentMatriculatedChanged -> handle(event)
            is StudentChargesChanged -> handle(event)
        }
    }

    private fun handle(event: StudentCreated) {
        this.status = event.matriculationStatus
        this.charges = event.charges
    }

    private fun handle(event: StudentMatriculatedChanged) {
        this.status = event.matriculationStatus
    }

    private fun handle(event: StudentChargesChanged) {
        this.charges = event.newCharges
    }

}