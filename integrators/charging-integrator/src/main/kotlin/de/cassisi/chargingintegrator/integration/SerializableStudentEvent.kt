package de.cassisi.chargingintegrator.integration

import com.google.gson.annotations.SerializedName
import java.util.UUID

sealed interface SerializableStudentEvent

data class SerializableLendingViolationOccurred(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
): SerializableStudentEvent

data class SerializableLendingViolationResolved(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
): SerializableStudentEvent
