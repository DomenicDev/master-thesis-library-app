package de.cassisi.lending.student.charge

import de.cassisi.lending.student.ChargeStudentCommand

interface ChargeStudent {

    fun execute(command: ChargeStudentCommand)

}