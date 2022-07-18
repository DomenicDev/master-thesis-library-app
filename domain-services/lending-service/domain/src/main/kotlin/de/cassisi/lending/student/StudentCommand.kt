package de.cassisi.lending.student

sealed interface StudentCommand

data class CreateStudent(
    val studentId: StudentId,
    val status: MatriculationStatus,
    val charges: Charges
): StudentCommand

data class ChargeStudent(
    val studentId: StudentId,
): StudentCommand

data class UpdateMatriculationStatus(
    val studentId: StudentId,
    val newStatus: MatriculationStatus
): StudentCommand