package de.cassisi.student.student

import de.cassisi.student.common.BaseAggregate
import de.cassisi.student.common.Version

class StudentAggregate(id: StudentId, version: Version) : Student, BaseAggregate<StudentId, StudentEvent>(id, version) {

    private lateinit var name: Name
    private lateinit var email: Email
    private lateinit var matriculationNumber: MatriculationNumber
    private lateinit var status: MatriculationStatus

    override fun getName(): Name {
        return name
    }

    override fun getMatriculationNumber(): MatriculationNumber {
        return matriculationNumber
    }

    override fun getEmail(): Email {
        return email
    }

    override fun getMatriculationStatus(): MatriculationStatus {
        return this.status
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
        }
    }

    private fun handle(event: StudentCreated) {
        this.name = event.name
        this.email = event.email
        this.matriculationNumber = event.matriculationNumber
        this.status = event.matriculationStatus
    }

    private fun handle(event: StudentMatriculatedChanged) {
        this.status = event.matriculationStatus
    }

}