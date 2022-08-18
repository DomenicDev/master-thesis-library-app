package de.cassisi.lending.student

sealed interface StudentCommand

data class CreateStudentCommand(
    val studentId: StudentId,
    val matriculationStatus: MatriculationStatus,
    val lockStatus: LockStatus
): StudentCommand

data class UpdateMatriculationStatusCommand(
    val studentId: StudentId,
    val newStatus: MatriculationStatus
): StudentCommand

