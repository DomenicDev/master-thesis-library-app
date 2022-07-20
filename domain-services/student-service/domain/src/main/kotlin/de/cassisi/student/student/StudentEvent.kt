package de.cassisi.student.student

sealed interface StudentEvent

data class StudentCreated(
    val id: StudentId,
    val name: Name,
    val email: Email,
    val matriculationNumber: MatriculationNumber,
    val matriculationStatus: MatriculationStatus,
) : StudentEvent

data class StudentMatriculatedChanged(
    val studentId: StudentId,
    val matriculationStatus: MatriculationStatus
) : StudentEvent

