package de.cassisi.lending.student.register

import de.cassisi.lending.student.CreateStudentCommand
import de.cassisi.lending.student.StudentFactory

class RegisterStudentExecutor(private val repository: RegisterStudentRepository) : RegisterStudent {

    override fun execute(command: CreateStudentCommand) {
        val student = StudentFactory.create(command)
        this.repository.save(student)
    }

}