package de.cassisi.catalogue.campus

import java.util.*

data class AddCampusRequest(
    val campusId: UUID,
    val campusLocation: String
)