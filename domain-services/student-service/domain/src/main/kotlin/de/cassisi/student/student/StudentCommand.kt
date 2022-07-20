package de.cassisi.student.student

sealed interface StudentCommand

data class CreateStudentCommand(
    val studentId: StudentId,
    val name: Name,
    val email: Email,
    val matriculationNumber: MatriculationNumber,
    val status: MatriculationStatus,
): StudentCommand

data class UpdateMatriculationStatusCommand(
    val studentId: StudentId,
    val newStatus: MatriculationStatus
): StudentCommand

