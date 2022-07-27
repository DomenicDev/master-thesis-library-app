package de.cassisi.catalogueprojector.subscription

import com.google.gson.annotations.SerializedName
import java.util.*

data class SerializableMetadataAdded(
    @SerializedName("metadataId") val metadataId: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("publisher") val publisher: String
)

data class CampusOpenedSerializable(
    @SerializedName("campusId")         val campusId: UUID,
    @SerializedName("campusLocation")   val campusLocation: String
)

data class BookAddedToCatalogueSerializable(
    @SerializedName("bookId")       val bookId: UUID,
    @SerializedName("metadataId")   val metadataId: UUID,
    @SerializedName("campusId")     val campusId: UUID,
    @SerializedName("signature")    val signature: String
)

data class BookSignatureUpdatedSerializable(
    @SerializedName("bookId")       val bookId: UUID,
    @SerializedName("campusId")     val campusId: UUID,
    @SerializedName("oldSignature") val oldSignature: String,
    @SerializedName("newSignature") val newSignature: String
)
