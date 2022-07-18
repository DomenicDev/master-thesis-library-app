package de.cassisi.lending.book

import de.cassisi.lending.common.BaseAggregate
import de.cassisi.lending.common.Version

class BorrowableBookAggregate(id: BookId, version: Version) : BorrowableBook, BaseAggregate<BookId, BookEvent>(id, version) {

    private var available: Boolean = false

    override fun isAvailableForLoan(): Boolean {
        return this.available
    }

    override fun execute(command: BookCommand.BorrowBookCommand) {
        if (!isAvailableForLoan()) {
            throw BookAlreadyLoanException(getId())
        }
        val bookBorrowedEvent = BookBorrowed(getId())
        registerEvent(bookBorrowedEvent)
    }

    override fun execute(command: BookCommand.ReturnBookCommand) {
        val bookReturnedEvent = BookReturned(getId())
        registerEvent(bookReturnedEvent)
    }

    override fun handleEvent(event: BookEvent) {
        when (event) {
            is BorrowableBookAdded -> handle(event)
            is BookBorrowed -> handle(event)
            is BookReturned -> handle(event)
        }
    }

    private fun handle(event: BorrowableBookAdded) {
        this.available = true
    }

    private fun handle(event: BookBorrowed) {
        this.available = true
    }

    private fun handle(event: BookReturned) {
        this.available = false
    }
}