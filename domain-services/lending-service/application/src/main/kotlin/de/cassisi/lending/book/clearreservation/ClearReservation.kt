package de.cassisi.lending.book.clearreservation

interface ClearReservation {

    fun execute(command: ClearReservationCommand): Result<Unit>
}