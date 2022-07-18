package de.cassisi.lending.student.resetcharge

import de.cassisi.lending.student.ResetChargesCommand

class ResetChargesExecutor(private val repository: ResetChargesRepository) : ResetCharges {

    override fun execute(command: ResetChargesCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }

}