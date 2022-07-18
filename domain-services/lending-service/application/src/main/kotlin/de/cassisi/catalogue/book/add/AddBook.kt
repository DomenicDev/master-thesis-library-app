package de.cassisi.catalogue.book.add

import de.cassisi.catalogue.book.BookCommand

interface AddBook {

    fun execute(command: BookCommand.AddBook)

}