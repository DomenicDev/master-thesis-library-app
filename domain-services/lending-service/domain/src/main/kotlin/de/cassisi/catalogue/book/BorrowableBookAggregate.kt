package de.cassisi.catalogue.book

import de.cassisi.catalogue.common.BaseAggregate
import de.cassisi.catalogue.common.Version

class BorrowableBookAggregate(id: BookId, version: Version) : BorrowableBook, BaseAggregate<BookId, BookEvent>(id, version) {

    private var available: Boolean = false

    override fun isAvailableForLoan(): Boolean {
        return this.available
    }

    override fun execute(command: BorrowBookCommand) {
        if (!isAvailableForLoan()) {
            throw BookAlreadyLoanException(getId())
        }
        val bookBorrowedEvent = BookBorrowed(getId())
        registerEvent(bookBorrowedEvent)
    }

    override fun execute(command: ReturnBookCommand) {
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