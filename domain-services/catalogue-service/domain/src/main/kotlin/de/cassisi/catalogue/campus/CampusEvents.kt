package de.cassisi.catalogue.campus

sealed interface CampusEvents {

    data class CampusOpened(
        val campusId: CampusId,
        val campusLocation: CampusLocation
    ) : CampusEvents

}