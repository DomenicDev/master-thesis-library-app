package de.cassisi.lending.book.reserve

class ReserveBookExecutor(private val repository: ReserveBookRepository) : ReserveBook {

    override fun execute(command: ReserveBookCommand): Result<Unit> {
        val book = repository.getById(command.bookId)
        val result = book.reserveBook(command.studentId, command.reservationDate)
        result.onSuccess { repository.save(book) }
        return result
    }
}