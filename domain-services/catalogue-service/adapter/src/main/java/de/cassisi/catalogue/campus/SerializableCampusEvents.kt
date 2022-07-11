package de.cassisi.catalogue.campus

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class CampusOpenedSerializable(
    @SerializedName("campusId")         val campusId: UUID,
    @SerializedName("campusLocation")   val campusLocation: String
)