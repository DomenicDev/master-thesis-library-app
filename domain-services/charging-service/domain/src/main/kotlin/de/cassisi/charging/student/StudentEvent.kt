package de.cassisi.charging.student

sealed interface StudentEvent

data class StudentChargeAccountCreated(
    val id: StudentId,
    val charges: Charges
) : StudentEvent

data class StudentCharged(
    val studentId: StudentId,
    val currentCharges: Charges
) : StudentEvent

data class StudentChargesPaid(
    val studentId: StudentId,
    val currentCharges: Charges
): StudentEvent

data class LendingViolationOccurred(
    val studentId: StudentId,
    val currentCharges: Charges
): StudentEvent

data class LendingViolationResolved(
    val studentId: StudentId,
    val currentCharges: Charges
): StudentEvent