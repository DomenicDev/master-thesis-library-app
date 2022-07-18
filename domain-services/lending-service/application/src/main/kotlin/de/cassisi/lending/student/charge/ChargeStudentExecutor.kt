package de.cassisi.lending.student.charge

import de.cassisi.lending.student.ChargeStudentCommand

class ChargeStudentExecutor(private val repository: ChargeStudentRepository) : ChargeStudent {

    override fun execute(command: ChargeStudentCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.execute(command)
        repository.save(student)
    }

}