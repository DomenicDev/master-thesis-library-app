package de.cassisi.catalogue.metadata

import de.cassisi.catalogue.common.EventSourcedAggregate

interface Metadata: EventSourcedAggregate<MetadataId, MetadataEvent> {

    fun getTitle(): Title
    fun getAuthor(): Author
    fun getISBN(): ISBN
    fun getPublisher(): Publisher

}