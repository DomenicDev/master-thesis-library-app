package de.cassisi.charging.student.clear

import de.cassisi.charging.student.ClearChargesCommand

interface ClearCharges {

    fun execute(command: ClearChargesCommand)

}