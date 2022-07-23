package de.cassisi.charging.student

import de.cassisi.charging.common.BaseAggregate
import de.cassisi.charging.common.Version

class StudentAggregate(id: StudentId, version: Version) : Student, BaseAggregate<StudentId, StudentEvent>(id, version) {

    private var charges: Charges = Charges(0)

    override fun handleEvent(event: StudentEvent) {
        when (event) {
            is StudentChargeAccountCreated -> handle(event)
            is StudentCharged -> handle(event)
            is StudentChargesPaid -> handle(event)
        }
    }

    private fun handle(event: StudentChargeAccountCreated) {
        this.charges = Charges(0)
    }

    private fun handle(event: StudentCharged) {
        this.charges = event.currentCharges
    }

    private fun handle(event: StudentChargesPaid) {
        this.charges = event.currentCharges
    }

    override fun getCharges(): Charges {
        return this.charges
    }

    override fun execute(command: ChargeStudentCommand) {
        val newCharges = this.charges.add(command.amount.charges)
        val event = StudentCharged(
            getId(),
            newCharges
        )
        registerEvent(event)
    }

    override fun execute(command: ClearChargesCommand) {
        val event = StudentChargesPaid(
            getId(),
            Charges(0)
        )
        registerEvent(event)
    }
}