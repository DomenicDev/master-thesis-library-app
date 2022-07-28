package de.cassisi.lendingprojector.subscription

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*


data class SerializableBookAdded(
    @SerializedName("bookId") val bookId: UUID
)

data class SerializableBookBorrowed(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate
)

data class SerializableBookReturned(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("returnDate") val returnDate: LocalDate
)

data class SerializableLoanExtended(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate
)