package de.cassisi.charging.student.charge

import de.cassisi.charging.student.ChargeStudentCommand

interface ChargeStudent {

    fun execute(command: ChargeStudentCommand)

}