package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookCommand.UpdateBookSignature
import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.common.EventSourcedAggregate
import de.cassisi.catalogue.metadata.MetadataId

sealed interface Book: EventSourcedAggregate<BookId, BookEvent> {

    fun getMetadataId(): MetadataId
    fun getCampusId(): CampusId
    fun getSignature(): Signature

    fun execute(command: UpdateBookSignature)
}