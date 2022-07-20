package de.cassisi.student.student.register

import de.cassisi.student.student.CreateStudentCommand
import de.cassisi.student.student.StudentFactory

class RegisterStudentExecutor(private val repository: RegisterStudentRepository) : RegisterStudent {

    override fun execute(command: CreateStudentCommand) {
        val student = StudentFactory.create(command)
        this.repository.save(student)
    }

}