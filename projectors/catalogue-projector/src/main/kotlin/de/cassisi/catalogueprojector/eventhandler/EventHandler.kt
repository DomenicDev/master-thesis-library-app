package de.cassisi.catalogueprojector.eventhandler

import de.cassisi.catalogueprojector.dbmodel.*
import de.cassisi.catalogueprojector.subscription.BookAddedToCatalogueSerializable
import de.cassisi.catalogueprojector.subscription.BookSignatureUpdatedSerializable
import de.cassisi.catalogueprojector.subscription.CampusOpenedSerializable
import de.cassisi.catalogueprojector.subscription.SerializableMetadataAdded
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val metadataRepository: MetadataRepository,
    private val campusRepository: CampusRepository,
    private val bookRepository: BookRepository
) {

    fun handle(event: BookAddedToCatalogueSerializable) {
        addToMetadataRepository(event)
        addToBookRepository(event)
    }

    private fun addToBookRepository(event: BookAddedToCatalogueSerializable) {
        val location = campusRepository.findById(event.campusId.toString()).get().location
        val document = BookDocument(
            event.bookId.toString(),
            event.campusId.toString(),
            location,
            event.metadataId.toString(),
            event.signature
        )
        bookRepository.save(document)
    }

    private fun addToMetadataRepository(event: BookAddedToCatalogueSerializable) {
        val document = metadataRepository.findById(event.metadataId.toString()).get()
        val campus = campusRepository.findById(event.campusId.toString()).get()
        val book = BookCopy(
            event.bookId.toString(),
            campus.campusId,
            campus.location,
            event.signature
        )
        val newDocument = document.copy(copies = document.copies + book)
        metadataRepository.save(newDocument)
    }

    fun handle(event: BookSignatureUpdatedSerializable) {
        val book = bookRepository.findById(event.bookId.toString()).get()
        val newBook = book.copy(signature = event.newSignature)
        bookRepository.save(newBook)

        val metadata = metadataRepository.findById(book.metadataId).get()
        val updatedBook = metadata.copies.first { it.bookId == book.bookId }.copy(signature = event.newSignature)
        val copies = metadata.copies.filterNot { it.bookId == book.bookId } + updatedBook
        val updatedDocument = metadata.copy(copies = copies)
        metadataRepository.save(updatedDocument)
    }

    fun handle(event: CampusOpenedSerializable) {
        val document = CampusDocument(
            event.campusId.toString(),
            event.campusLocation
        )
        campusRepository.save(document)
    }

    fun handle(event: SerializableMetadataAdded) {
        val document = MetadataDocument(
            event.metadataId.toString(),
            event.title,
            event.author,
            event.isbn,
            event.publisher,
            emptyList()
        )
        metadataRepository.save(document)
    }

}