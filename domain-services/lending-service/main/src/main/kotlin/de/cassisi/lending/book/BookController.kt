package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/book")
class BookController(private val service: BookService) {

    @PostMapping
    fun addBook(@RequestBody request: AddBookRequest) {
        val bookId = BookId(request.bookId)
        service.addBook(bookId)
    }

    @PostMapping("/borrow")
    fun borrowBook(@RequestBody request: BorrowBookRequest) {
        val bookId = BookId(request.bookId)
        val studentId = StudentId(request.studentId)
        val startDate = request.startDate
        service.borrowBook(bookId, studentId, startDate)
    }

    @PostMapping("/extend")
    fun extendLoan(@RequestBody request: ExtendLoanRequest) {
        val bookId = BookId(request.bookId)
        service.extendLoan(bookId)
    }

    @PostMapping("/return")
    fun returnBook(@RequestBody request: ReturnBookRequest) {
        val bookId = BookId(request.bookId)
        val returnDate = request.returnDate
        service.returnBook(bookId, returnDate)
    }

    @PostMapping("/reservation")
    fun reserveBook(@RequestBody request: ReserveBookRequest): ResponseEntity<Unit> {
        val bookId = BookId(request.bookId)
        val studentId = StudentId(request.studentId)
        val reservationDate = request.reservationDate
        val result = service.reserveBook(bookId, studentId, reservationDate)

        return result.fold(
            { ResponseEntity.ok().build() },
            { ResponseEntity.badRequest().build() }
        )
    }

    @PostMapping("/reservation/clear")
    fun clearReservation(@RequestBody request: ClearReservationRequest) {
        val bookId = BookId(request.bookId)
        service.clearReservation(bookId)
    }

    // ----------------------- //
    // REQUEST DATA STRUCTURES //

    data class AddBookRequest(
        val bookId: UUID
    )

    data class BorrowBookRequest(
        val bookId: UUID,
        val studentId: UUID,
        val startDate: LocalDate
    )

    data class ExtendLoanRequest(
        val bookId: UUID
    )

    data class ReturnBookRequest(
        val bookId: UUID,
        val returnDate: LocalDate
    )

    data class ReserveBookRequest(
        val bookId: UUID,
        val studentId: UUID,
        val reservationDate: LocalDate
    )

    data class ClearReservationRequest(
        val bookId: UUID
    )
}