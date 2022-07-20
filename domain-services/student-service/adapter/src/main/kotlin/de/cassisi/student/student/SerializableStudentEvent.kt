package de.cassisi.student.student

import com.google.gson.annotations.SerializedName
import java.util.UUID

sealed interface SerializableStudentEvent

data class SerializableStudentCreated(
    @SerializedName("id")       val id: UUID,
                                val name: SerializableName,
    @SerializedName("email")    val email: String,
    @SerializedName("matriculationNumber") val matriculationNumber: Int,
    @SerializedName("matriculationStatus")   val matriculationStatus: Boolean,
): SerializableStudentEvent

data class SerializableStudentMatriculationChanged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("matriculationStatus") val status: Boolean
): SerializableStudentEvent


data class SerializableName(
    @SerializedName("forename") val forename: String,
    @SerializedName("lastname") val lastname: String
)