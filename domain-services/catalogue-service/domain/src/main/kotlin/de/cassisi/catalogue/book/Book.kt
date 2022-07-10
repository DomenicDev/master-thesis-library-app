package de.cassisi.catalogue.book

import de.cassisi.catalogue.book.BookCommand.*
import de.cassisi.catalogue.campus.CampusId

interface Book {

    fun getBookId(): BookId
    fun getCampusId(): CampusId
    fun getSignature(): Signature

    fun execute(command: UpdateBookSignature)
}