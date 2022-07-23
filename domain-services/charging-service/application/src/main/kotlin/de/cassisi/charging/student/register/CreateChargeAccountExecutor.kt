package de.cassisi.charging.student.register

import de.cassisi.charging.student.CreateChargingAccountCommand
import de.cassisi.charging.student.StudentFactory

class CreateChargeAccountExecutor(private val repository: CreateChargeAccountRepository) : CreateChargeAccount {

    override fun execute(command: CreateChargingAccountCommand) {
        val student = StudentFactory.create(command)
        this.repository.save(student)
    }

}