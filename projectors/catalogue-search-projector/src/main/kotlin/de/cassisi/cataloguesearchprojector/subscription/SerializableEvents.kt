package de.cassisi.cataloguesearchprojector.subscription

import com.google.gson.annotations.SerializedName
import java.util.*

data class SerializableMetadataAdded(
    @SerializedName("metadataId") val metadataId: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("author") val author: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("publisher") val publisher: String
)
