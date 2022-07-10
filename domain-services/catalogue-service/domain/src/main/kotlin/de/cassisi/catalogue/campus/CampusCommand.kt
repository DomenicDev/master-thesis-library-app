package de.cassisi.catalogue.campus

sealed interface CampusCommand

data class CreateCampus(
    val campusId: CampusId,
    val campusLocation: CampusLocation
) : CampusCommand