package de.cassisi.studentprojector.subscription

import com.google.gson.annotations.SerializedName
import java.util.*


data class SerializableStudentCreated(
    @SerializedName("id")       val studentId: UUID,
    val name: SerializableName,
    @SerializedName("email")    val email: String,
    @SerializedName("matriculationNumber") val matriculationNumber: Int,
    @SerializedName("matriculationStatus")   val matriculationStatus: Boolean,
)

data class SerializableStudentMatriculationChanged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("matriculationStatus") val status: Boolean
)


data class SerializableName(
    @SerializedName("forename") val forename: String,
    @SerializedName("lastname") val lastname: String
)