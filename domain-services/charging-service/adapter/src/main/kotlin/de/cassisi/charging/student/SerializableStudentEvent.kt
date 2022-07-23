package de.cassisi.charging.student

import com.google.gson.annotations.SerializedName
import java.util.UUID

sealed interface SerializableStudentEvent

data class SerializableStudentChargeAccountCreated(
    @SerializedName("studentId") val id: UUID,
    @SerializedName("charges") val charges: Int
) : SerializableStudentEvent

data class SerializableStudentCharged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
) : SerializableStudentEvent

data class SerializableStudentChargesPaid(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
): SerializableStudentEvent
