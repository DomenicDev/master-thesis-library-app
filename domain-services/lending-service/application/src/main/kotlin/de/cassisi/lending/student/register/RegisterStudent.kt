package de.cassisi.lending.student.register

import de.cassisi.lending.student.CreateStudentCommand

interface RegisterStudent {

    fun execute(command: CreateStudentCommand)

}