package de.cassisi.charging.student.clear

import de.cassisi.charging.student.ClearChargesCommand

class ClearChargesExecutor(private val repository: ClearChargesRepository) : ClearCharges {

    override fun execute(command: ClearChargesCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }
}