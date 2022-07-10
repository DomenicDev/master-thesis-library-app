package de.cassisi.catalogue.campus

import de.cassisi.catalogue.common.BaseAggregate
import de.cassisi.catalogue.common.Version

class CampusAggregate(campusId: CampusId, version: Version) : Campus, BaseAggregate<CampusId, CampusEvent>(campusId, version) {

    private lateinit var campusLocation: CampusLocation

    override fun handleEvent(event: CampusEvent) {
        when (event) {
            is CampusOpened -> apply(event)
        }
    }

    private fun apply(event: CampusOpened) {
        this.campusLocation = event.campusLocation
    }

    override fun getCampusInformation(): CampusLocation {
        return this.campusLocation
    }

}