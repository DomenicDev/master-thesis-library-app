package de.cassisi.catalogue.metadata

import de.cassisi.catalogue.common.BaseAggregate
import de.cassisi.catalogue.common.Version

class MetadataAggregate(id: MetadataId, version: Version) : Metadata, BaseAggregate<MetadataId, MetadataEvent>(id, version) {

    private lateinit var title: Title
    private lateinit var author: Author
    private lateinit var isbn: ISBN
    private lateinit var publisher: Publisher


    override fun handleEvent(event: MetadataEvent) {
        when (event) {
            is MetadataAdded -> handle(event)
        }
    }

    private fun handle(event: MetadataAdded) {
        this.title = event.title
        this.author = event.author
        this.isbn = event.isbn
        this.publisher = event.publisher
    }

    override fun getTitle(): Title {
        return this.title
    }

    override fun getAuthor(): Author {
        return this.author
    }

    override fun getISBN(): ISBN {
        return this.isbn
    }

    override fun getPublisher(): Publisher {
        return this.publisher
    }
}