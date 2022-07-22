package de.cassisi.catalogueintegrator.integration

import com.google.gson.annotations.SerializedName
import java.util.*

data class BookAddedToCatalogueSerializable(
    @SerializedName("bookId")       val bookId: UUID,
    @SerializedName("metadataId")   val metadataId: UUID,
    @SerializedName("campusId")     val campusId: UUID,
    @SerializedName("signature")    val signature: String
)