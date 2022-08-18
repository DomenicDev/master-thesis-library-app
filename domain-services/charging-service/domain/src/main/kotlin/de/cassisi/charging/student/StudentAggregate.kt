package de.cassisi.charging.student

import de.cassisi.charging.common.BaseAggregate
import de.cassisi.charging.common.Version

class StudentAggregate(id: StudentId, version: Version) : Student, BaseAggregate<StudentId, StudentEvent>(id, version) {

    private var charges: Charges = Charges(0)
    private var lendingViolationActive: Boolean = false

    override fun handleEvent(event: StudentEvent) {
        when (event) {
            is StudentChargeAccountCreated -> handle(event)
            is StudentCharged -> handle(event)
            is StudentChargesPaid -> handle(event)
            is LendingViolationOccurred -> handle(event)
            is LendingViolationResolved -> handle(event)
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

    private fun handle(event: LendingViolationOccurred) {
        this.lendingViolationActive = true
    }

    private fun handle(event: LendingViolationResolved) {
        this.lendingViolationActive = false
    }

    override fun getCharges(): Charges {
        return this.charges
    }

    override fun execute(command: ChargeStudentCommand) {
        // charge student
        val newCharges = this.charges.add(command.amount.charges)
        val event = StudentCharged(
            getId(),
            newCharges
        )
        registerEvent(event)

        // check for lending violation
        if (!lendingViolationActive && newCharges.charges > 25) {
            val violationEvent = LendingViolationOccurred(getId(), newCharges)
            registerEvent(violationEvent)
        }
    }

    override fun execute(command: ClearChargesCommand) {
        // clear charges
        val event = StudentChargesPaid(
            getId(),
            Charges(0)
        )
        registerEvent(event)

        // resolve lending violation
        if (lendingViolationActive) {
            val resolvedEvent = LendingViolationResolved(getId(), getCharges())
            registerEvent(resolvedEvent)
        }
    }
}