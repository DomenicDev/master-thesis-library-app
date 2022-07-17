package de.cassisi.catalogue.student

sealed interface StudentEvent

data class StudentCreated(
    val id: StudentId,
    val matriculationStatus: MatriculationStatus,
    val charges: Charges
) : StudentEvent

data class StudentMatriculatedChanged(
    val student: StudentId,
    val matriculationStatus: MatriculationStatus
) : StudentEvent

data class StudentChargesChanged(
    val student: StudentId,
    val newCharges: Charges
): StudentEvent