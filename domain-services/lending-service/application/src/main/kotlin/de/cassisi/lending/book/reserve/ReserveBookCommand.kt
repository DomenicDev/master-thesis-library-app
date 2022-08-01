package de.cassisi.lending.book.reserve

import de.cassisi.lending.book.BookId
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

data class ReserveBookCommand(
    val bookId: BookId,
    val studentId: StudentId,
    val reservationDate: LocalDate
)
