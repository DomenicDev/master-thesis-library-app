package de.cassisi.catalogue.book

import de.cassisi.catalogue.common.EventSourcedAggregate

sealed interface BorrowableBook: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean

    fun execute(command: BookCommand.BorrowBookCommand)

    fun execute(command: BookCommand.ReturnBookCommand)

}