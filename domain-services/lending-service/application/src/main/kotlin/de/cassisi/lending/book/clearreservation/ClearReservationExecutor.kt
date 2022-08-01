package de.cassisi.lending.book.clearreservation

class ClearReservationExecutor(private val repository: ClearReservationRepository) : ClearReservation {

    override fun execute(command: ClearReservationCommand): Result<Unit> {
        val book = repository.getById(command.bookId)
        val result = book.clearReservation()
        result.onSuccess { repository.save(book) }
        return result
    }
}