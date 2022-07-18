package de.cassisi.lending.book.add

import de.cassisi.lending.book.BookCommand

interface AddBook {

    fun execute(command: BookCommand.AddBook)

}