package de.cassisi.lending.loan

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

sealed interface SerializableLoanEvent

data class SerializableLoanCreated(
    @SerializedName("loanId")       val loanId: UUID,
    @SerializedName("studentId")    val studentId: UUID,
    @SerializedName("bookId")       val bookId: UUID,
    @SerializedName("startDate")    val startDate: LocalDate,
    @SerializedName("endDate")      val endDate: LocalDate,
): SerializableLoanEvent

data class SerializableLoanExtended(
    @SerializedName("loanId")       val loanId: UUID,
    @SerializedName("startDate")    val startDate: LocalDate,
    @SerializedName("endDate")      val endDate: LocalDate,
    @SerializedName("extensions")   val extensions: Int
) : SerializableLoanEvent

data class SerializableLoanReturned(
    @SerializedName("loanId")       val loanId: UUID,
    @SerializedName("returnDate")   val returnDate: LocalDate
) : SerializableLoanEvent