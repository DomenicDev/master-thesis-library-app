package de.cassisi.chargingprojector.subscription

import com.google.gson.annotations.SerializedName
import java.util.*


data class SerializableStudentChargeAccountCreated(
    @SerializedName("studentId") val id: UUID,
    @SerializedName("charges") val charges: Int
)

data class SerializableStudentCharged(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
)

data class SerializableStudentChargesPaid(
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("currentCharges") val currentCharges: Int
)
