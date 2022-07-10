package de.cassisi.catalogue.book

import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.metadata.MetadataId

sealed interface BookCommand {

    data class AddBook(
        val bookId: BookId,
        val metadataId: MetadataId,
        val campusId: CampusId,
        val signature: Signature
    ) : BookCommand

    data class UpdateBookSignature(
        val bookId: BookId,
        val newSignature: Signature,
    ) : BookCommand

}