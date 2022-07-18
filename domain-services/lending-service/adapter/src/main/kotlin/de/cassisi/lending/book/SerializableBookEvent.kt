package de.cassisi.lending.book

import com.google.gson.annotations.SerializedName
import java.util.UUID

sealed interface SerializableBookEvent

data class SerializableBorrowableBookAdded(
    @SerializedName("bookId") val bookId: UUID
): SerializableBookEvent

data class SerializableBookBorrowed(
    @SerializedName("bookId") val bookId: UUID
): SerializableBookEvent

data class SerializableBookReturned(
    @SerializedName("bookId") val bookId: UUID
): SerializableBookEvent