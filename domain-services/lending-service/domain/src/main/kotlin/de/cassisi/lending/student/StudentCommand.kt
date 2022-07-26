package de.cassisi.lending.student

sealed interface StudentCommand

data class CreateStudentCommand(
    val studentId: StudentId,
    val matriculationStatus: MatriculationStatus,
    val charges: Charges
): StudentCommand

data class UpdateStudentChargesCommand(
    val studentId: StudentId,
    val currentCharges: Charges,
): StudentCommand


data class UpdateMatriculationStatusCommand(
    val studentId: StudentId,
    val newStatus: MatriculationStatus
): StudentCommand

