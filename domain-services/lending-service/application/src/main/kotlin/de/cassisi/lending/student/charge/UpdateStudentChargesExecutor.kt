package de.cassisi.lending.student.charge

import de.cassisi.lending.student.UpdateStudentChargesCommand


class UpdateStudentChargesExecutor(private val repository: UpdateStudentChargesRepository) : UpdateStudentCharges {

    override fun execute(command: UpdateStudentChargesCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }

}