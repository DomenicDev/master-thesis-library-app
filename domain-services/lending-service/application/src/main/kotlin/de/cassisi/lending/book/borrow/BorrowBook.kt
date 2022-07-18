package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BookCommand

interface BorrowBook {

    fun execute(command: BookCommand.BorrowBookCommand)

}