package de.cassisi.student.student.register

import de.cassisi.student.student.CreateStudentCommand

interface RegisterStudent {

    fun execute(command: CreateStudentCommand)

}