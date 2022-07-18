package de.cassisi.lending.book

import de.cassisi.lending.common.EventSourcedAggregate

sealed interface BorrowableBook: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean

    fun execute(command: BookCommand.BorrowBookCommand)

    fun execute(command: BookCommand.ReturnBookCommand)

}