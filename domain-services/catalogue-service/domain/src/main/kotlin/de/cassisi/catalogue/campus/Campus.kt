package de.cassisi.catalogue.campus

sealed interface Campus {

    fun getCampusId(): CampusId
    fun getCampusInformation(): CampusLocation

}