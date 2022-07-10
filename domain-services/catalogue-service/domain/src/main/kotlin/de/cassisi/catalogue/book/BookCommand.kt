package de.cassisi.catalogue.book

sealed interface BookCommand {

    data class UpdateBookSignature(
        val bookId: BookId,
        val newSignature: Signature,
    )

}