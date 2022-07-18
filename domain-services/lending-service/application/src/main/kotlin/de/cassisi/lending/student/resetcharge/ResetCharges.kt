package de.cassisi.lending.student.resetcharge

import de.cassisi.lending.student.ResetChargesCommand

interface ResetCharges {

    fun execute(command: ResetChargesCommand)

}