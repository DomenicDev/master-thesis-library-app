package de.cassisi.catalogue.book.borrow

import de.cassisi.catalogue.book.BookCommand

interface BorrowBook {

    fun execute(command: BookCommand.BorrowBookCommand)

}