package de.cassisi.student.student.updatematriculationstatus

import de.cassisi.student.student.UpdateMatriculationStatusCommand

class UpdateMatriculationStatusExecutor(private val repository: UpdateMatriculationStatusRepository) : UpdateMatriculationStatus {

    override fun execute(command: UpdateMatriculationStatusCommand) {
        val student = repository.getById(command.studentId)
        student.execute(command)
        repository.save(student)
    }
}