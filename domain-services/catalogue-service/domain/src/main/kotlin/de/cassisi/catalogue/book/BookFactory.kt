package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookEvent.BookAddedToCatalogue
import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.common.Version

object BookFactory {

    fun create(bookId: BookId, campusId: CampusId, signature: Signature): Book {
        val book = BookAggregate(bookId, Version.init())
        val event = BookAddedToCatalogue(bookId, campusId, signature)
        book.registerEvent(event)
        return book
    }

}