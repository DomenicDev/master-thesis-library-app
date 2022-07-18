package de.cassisi.catalogue.book.returnbook

import de.cassisi.catalogue.book.BookCommand

interface ReturnBook {

    fun execute(command: BookCommand.ReturnBookCommand)

}