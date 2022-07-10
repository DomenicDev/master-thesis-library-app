package de.cassisi.catalogue.book

import com.google.gson.annotations.SerializedName
import java.util.*


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
