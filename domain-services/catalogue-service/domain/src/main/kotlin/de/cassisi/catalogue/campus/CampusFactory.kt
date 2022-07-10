package de.cassisi.catalogue.campus

import de.cassisi.catalogue.common.Versions

object CampusFactory {

    fun create(command: CreateCampus): Campus {
        // prepare fields
        val campusId = command.campusId
        val campusLocation = command.campusLocation

        // create aggregate
        val campus = CampusAggregate(campusId, Versions.init())
        val createdEvent = CampusOpened(campusId, campusLocation)
        campus.registerEvent(createdEvent)
        return campus
    }

}