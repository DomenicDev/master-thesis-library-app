package de.cassisi.lending.student

import com.google.gson.annotations.SerializedName
import java.util.*

sealed interface SerializableStudentEvent

data class SerializableStudentCreated(
    @SerializedName("id")       val id: UUID,
    @SerializedName("status")   val matriculationStatus: Boolean,
    @SerializedName("charges")  val charges: Int
): SerializableStudentEvent

data class SerializableStudentMatriculationChanged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("matriculationStatus") val status: Boolean
): SerializableStudentEvent

data class SerializableStudentChargesChanged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("newCharges") val newCharges: Int
): SerializableStudentEvent

