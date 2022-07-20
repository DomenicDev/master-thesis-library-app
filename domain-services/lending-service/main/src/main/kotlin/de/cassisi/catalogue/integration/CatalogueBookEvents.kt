package de.cassisi.catalogue.integration

import com.google.gson.annotations.SerializedName
import java.util.*

sealed interface CatalogueBookEvents

data class BookAddedToCatalogueSerializable(
    @SerializedName("bookId")       val bookId: UUID,
    @SerializedName("metadataId")   val metadataId: UUID,
    @SerializedName("campusId")     val campusId: UUID,
    @SerializedName("signature")    val signature: String
)

