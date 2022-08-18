package de.cassisi.lending.student

sealed interface StudentEvent

data class StudentCreated(
    val id: StudentId,
    val matriculationStatus: MatriculationStatus,
    val lockStatus: LockStatus
) : StudentEvent

data class StudentMatriculatedChanged(
    val studentId: StudentId,
    val matriculationStatus: MatriculationStatus
) : StudentEvent

data class StudentLockStatusChanged(
    val student: StudentId,
    val lockStatus: LockStatus
): StudentEvent
