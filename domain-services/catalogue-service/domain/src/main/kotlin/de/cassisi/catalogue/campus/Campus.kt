package de.cassisi.catalogue.campus

import de.cassisi.catalogue.common.EventSourcedAggregate

sealed interface Campus: EventSourcedAggregate<CampusId, CampusEvent> {

    fun getCampusInformation(): CampusLocation

}