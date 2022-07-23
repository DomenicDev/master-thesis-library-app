package de.cassisi.charging.student

sealed interface StudentCommand

data class CreateChargingAccountCommand(
    val studentId: StudentId,
): StudentCommand

data class ChargeStudentCommand(
    val studentId: StudentId,
    val amount: Charges
): StudentCommand

data class ClearChargesCommand(
    val studentId: StudentId
): StudentCommand