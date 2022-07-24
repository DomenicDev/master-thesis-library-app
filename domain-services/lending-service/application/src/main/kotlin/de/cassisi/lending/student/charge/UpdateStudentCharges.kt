package de.cassisi.lending.student.charge

import de.cassisi.lending.student.UpdateStudentChargesCommand

interface UpdateStudentCharges {

    fun execute(command: UpdateStudentChargesCommand)

}