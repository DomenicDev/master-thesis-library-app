package de.cassisi.catalogue.campus

import de.cassisi.catalogue.common.Version

object CampusFactory {

    fun create(campusId: CampusId, campusLocation: CampusLocation): Campus {
        val campus = CampusAggregate(campusId, Version.init())
        val createdEvent = CampusEvents.CampusOpened(campusId, campusLocation)
        campus.registerEvent(createdEvent)
        return campus
    }

}