package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookCommand.UpdateBookSignature
import de.cassisi.catalogue.book.BookEvent.BookAddedToCatalogue
import de.cassisi.catalogue.book.BookEvent.BookSignatureUpdated
import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.common.BaseAggregate
import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.metadata.MetadataId

class BookAggregate(id: BookId, version: Version) : Book, BaseAggregate<BookId, BookEvent>(id, version) {

    private lateinit var metadataId: MetadataId
    private lateinit var campusId: CampusId
    private lateinit var signature: Signature


    override fun getMetadataId(): MetadataId {
        return this.metadataId
    }

    override fun getCampusId(): CampusId {
        return this.campusId
    }

    override fun getSignature(): Signature {
        return this.signature
    }

    override fun execute(command: UpdateBookSignature) {
        val oldSignature = this.signature
        val newSignature = command.newSignature
        val event = BookSignatureUpdated(getId(), getCampusId(), oldSignature, newSignature)
        registerEvent(event)
    }

    override fun handleEvent(event: BookEvent) {
        when (event) {
            is BookAddedToCatalogue -> handle(event)
            is BookSignatureUpdated -> handle(event)
        }
    }

    private fun handle(event: BookAddedToCatalogue) {
        this.campusId = event.campusId
        this.signature = event.signature
    }

    private fun handle(event: BookSignatureUpdated) {
        this.signature = event.newSignature
    }
}