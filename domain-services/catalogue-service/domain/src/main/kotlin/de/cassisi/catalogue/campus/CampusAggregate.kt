package de.cassisi.catalogue.campus

import de.cassisi.catalogue.campus.CampusEvents.CampusOpened
import de.cassisi.catalogue.common.Aggregate
import de.cassisi.catalogue.common.Version

class CampusAggregate(campusId: CampusId, version: Version) : Campus, Aggregate<CampusId, CampusEvents>(campusId, version) {

    private lateinit var campusLocation: CampusLocation

    override fun handleEvent(event: CampusEvents) {
        when (event) {
            is CampusOpened -> apply(event)
        }
    }

    private fun apply(event: CampusOpened) {
        this.campusLocation = event.campusLocation
    }

    override fun getCampusId(): CampusId {
        return this.id
    }

    override fun getCampusInformation(): CampusLocation {
        return this.campusLocation
    }

}