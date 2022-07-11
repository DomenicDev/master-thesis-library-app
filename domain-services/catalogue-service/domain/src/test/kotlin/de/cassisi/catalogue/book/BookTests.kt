package de.cassisi.catalogue.book

import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.metadata.MetadataId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class BookTests {

    @Test
    fun simpleBookAggregate() {
        val bookId = BookId(UUID.randomUUID())
        val metadata = MetadataId(UUID.randomUUID())
        val campusId = CampusId(UUID.randomUUID())
        val signature = Signature("FUWA-1234")

        val cmd = BookCommand.AddBook(bookId, metadata, campusId, signature)
        val aggregate = BookFactory.create(cmd)

        assertEquals(bookId, aggregate.getId())
        assertEquals(metadata, aggregate.getMetadataId())
        assertEquals(campusId, aggregate.getCampusId())
        assertEquals(signature, aggregate.getSignature())
    }

    @Test
    fun updatedBookAggregate() {
        val bookId = BookId(UUID.randomUUID())
        val metadata = MetadataId(UUID.randomUUID())
        val campusId = CampusId(UUID.randomUUID())
        val signature = Signature("FUWA-1234")

        val cmd = BookCommand.AddBook(bookId, metadata, campusId, signature)
        val aggregate = BookFactory.create(cmd)

        val newSignature = Signature("VS-25")
        val updateCmd = BookCommand.UpdateBookSignature(bookId, newSignature)
        aggregate.execute(updateCmd)

        assertEquals(bookId, aggregate.getId())
        assertEquals(metadata, aggregate.getMetadataId())
        assertEquals(campusId, aggregate.getCampusId())
        assertEquals(newSignature, aggregate.getSignature())
    }

}