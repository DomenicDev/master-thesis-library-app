package de.cassisi.catalogue.book

import de.cassisi.catalogue.common.EventSourcedAggregate

sealed interface Book: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean


}