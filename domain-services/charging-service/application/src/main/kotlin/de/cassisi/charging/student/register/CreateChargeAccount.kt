package de.cassisi.charging.student.register

import de.cassisi.charging.student.CreateChargingAccountCommand

interface CreateChargeAccount {

    fun execute(command: CreateChargingAccountCommand)

}