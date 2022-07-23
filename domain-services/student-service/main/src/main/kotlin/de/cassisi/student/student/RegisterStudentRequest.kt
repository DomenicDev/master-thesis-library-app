package de.cassisi.student.student

import java.util.*

data class RegisterStudentRequest(
    val forename: String,
    val lastname: String,
    val email: String,
    val matriculationNumber: Int,
    val matriculated: Boolean,
)

data class UpdateMatriculationStatusRequest(
    val studentId: UUID,
    val matriculated: Boolean
)