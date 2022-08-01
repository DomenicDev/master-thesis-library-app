package de.cassisi.lending.book.clearreservation

import de.cassisi.lending.book.BaseBookRepository
import de.cassisi.lending.book.BookEventStoreRepository

class ClearReservationEventStoreRepository(repository: BookEventStoreRepository) : ClearReservationRepository, BaseBookRepository(repository)