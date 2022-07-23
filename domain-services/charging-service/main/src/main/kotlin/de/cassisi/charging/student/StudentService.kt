package de.cassisi.charging.student

import de.cassisi.charging.student.charge.ChargeStudent
import de.cassisi.charging.student.clear.ClearCharges
import de.cassisi.charging.student.register.CreateChargeAccount
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val createChargeAccount: CreateChargeAccount,
    private val chargeStudent: ChargeStudent,
    private val clearCharges: ClearCharges
) {

    fun createChargingAccount(command: CreateChargingAccountCommand) {
        createChargeAccount.execute(command)
    }

    fun chargeStudent(command: ChargeStudentCommand) {
        chargeStudent.execute(command)
    }

    fun clearCharges(command: ClearChargesCommand) {
        clearCharges.execute(command)
    }

}