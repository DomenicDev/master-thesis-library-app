package de.cassisi.lending.book.reserve

interface ReserveBook {

    fun execute(command: ReserveBookCommand): Result<Unit>

}