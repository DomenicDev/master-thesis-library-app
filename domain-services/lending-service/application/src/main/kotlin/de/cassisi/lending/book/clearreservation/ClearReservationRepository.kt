package de.cassisi.lending.book.clearreservation

import de.cassisi.lending.book.Book
import de.cassisi.lending.book.BookId
import de.cassisi.lending.common.AggregateRepository

interface ClearReservationRepository : AggregateRepository<BookId, Book>