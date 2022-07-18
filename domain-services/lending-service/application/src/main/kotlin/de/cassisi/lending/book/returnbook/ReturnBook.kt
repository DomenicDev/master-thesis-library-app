package de.cassisi.lending.book.returnbook

import de.cassisi.lending.book.BookCommand

interface ReturnBook {

    fun execute(command: BookCommand.ReturnBookCommand)

}