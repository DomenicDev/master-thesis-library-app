package de.cassisi.lending.book

import com.google.gson.annotations.SerializedName
import de.cassisi.lending.student.StudentId
import java.time.LocalDate
import java.util.*

sealed interface SerializableBookEvent

data class SerializableBookAdded(
    @SerializedName("bookId") val bookId: UUID
): SerializableBookEvent

data class SerializableBookBorrowed(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate
): SerializableBookEvent

data class SerializableBookReturned(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("returnDate") val returnDate: LocalDate
): SerializableBookEvent

data class SerializableLoanExtended(
    @SerializedName("bookId") val bookId: UUID,
    @SerializedName("loanId") val loanId: UUID,
    @SerializedName("studentId") val studentId: UUID,
    @SerializedName("startDate") val startDate: LocalDate,
    @SerializedName("endDate") val endDate: LocalDate
): SerializableBookEvent