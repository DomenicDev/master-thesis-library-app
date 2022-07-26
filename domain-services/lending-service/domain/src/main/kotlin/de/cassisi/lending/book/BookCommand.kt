package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId
import java.time.LocalDate


sealed interface BookCommand {

    data class AddBook(
        val bookId: BookId
    ): BookCommand

    data class BorrowBookCommand(
        val bookId: BookId,
        val studentId: StudentId,
        val startDate: LocalDate
    ): BookCommand

    data class ReturnBookCommand(
        val bookId: BookId,
        val returnDate: LocalDate
    ): BookCommand

    data class ExtendLoan(
        val bookId: BookId
    ): BookCommand
}

