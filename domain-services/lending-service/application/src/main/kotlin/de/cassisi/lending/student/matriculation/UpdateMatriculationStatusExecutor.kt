package de.cassisi.lending.student.matriculation

import de.cassisi.lending.student.UpdateMatriculationStatusCommand

class UpdateMatriculationStatusExecutor(private val repository: UpdateMatriculationStatusRepository) : UpdateMatriculationStatus {

    override fun execute(command: UpdateMatriculationStatusCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }

}