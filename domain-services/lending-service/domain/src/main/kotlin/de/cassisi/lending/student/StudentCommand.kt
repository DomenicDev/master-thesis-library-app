package de.cassisi.lending.student

sealed interface StudentCommand

data class CreateStudentCommand(
    val studentId: StudentId,
    val status: MatriculationStatus,
    val charges: Charges
): StudentCommand

data class ChargeStudentCommand(
    val studentId: StudentId,
): StudentCommand

data class ResetChargesCommand(
    val studentId: StudentId
): StudentCommand

data class UpdateMatriculationStatusCommand(
    val studentId: StudentId,
    val newStatus: MatriculationStatus
): StudentCommand

