package de.cassisi.catalogue.book

import de.cassisi.catalogue.common.EventSourcedAggregate

sealed interface BorrowableBook: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean

    fun execute(command: BorrowBookCommand)

    fun execute(command: ReturnBookCommand)

}