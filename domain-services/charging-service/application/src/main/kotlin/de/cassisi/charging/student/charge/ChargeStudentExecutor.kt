package de.cassisi.charging.student.charge

import de.cassisi.charging.student.ChargeStudentCommand

class ChargeStudentExecutor(private val repository: ChargeStudentRepository) : ChargeStudent {

    override fun execute(command: ChargeStudentCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }
}