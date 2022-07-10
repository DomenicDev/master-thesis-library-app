package de.cassisi.catalogue.campus

sealed interface CampusEvent 


data class CampusOpened(
    val campusId: CampusId,
    val campusLocation: CampusLocation
) : CampusEvent