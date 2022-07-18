package de.cassisi.lending.student

sealed interface StudentEvent

data class StudentCreated(
    val id: StudentId,
    val matriculationStatus: MatriculationStatus,
    val charges: Charges
) : StudentEvent

data class StudentMatriculatedChanged(
    val studentId: StudentId,
    val matriculationStatus: MatriculationStatus
) : StudentEvent

data class StudentCharged(
    val student: StudentId,
    val newCharges: Charges
): StudentEvent

data class StudentChargesReset(
    val student: StudentId,
    val charges: Charges
): StudentEvent