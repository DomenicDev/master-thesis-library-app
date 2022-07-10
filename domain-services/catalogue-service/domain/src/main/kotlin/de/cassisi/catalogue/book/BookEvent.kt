package de.cassisi.catalogue.book

import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.metadata.MetadataId


sealed interface BookEvent {

    data class BookAddedToCatalogue(
        val bookId: BookId,
        val metadataId: MetadataId,
        val campusId: CampusId,
        val signature: Signature
    ) : BookEvent

    data class BookSignatureUpdated(
        val bookId: BookId,
        val campusId: CampusId,
        val oldSignature: Signature,
        val newSignature: Signature
    ): BookEvent

}