package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/book")
class BookController(private val apiService: APIService) {

    @PostMapping
    fun addBook(@RequestBody request: AddBookRequest) {
        apiService.addBook(request.bookId, request.campusId, request.metadataId, request.signature)
    }

    @PostMapping("/borrow")
    fun borrowBook(@RequestBody request: BorrowBookRequest) {
        apiService.borrowBook(request.bookId, request.studentId, request.startDate)
    }

    @PostMapping("/extend")
    fun extendLoan(@RequestBody request: ExtendLoanRequest) {
        apiService.extendLoan(request.bookId)
    }

    @PostMapping("/return")
    fun returnBook(@RequestBody request : ReturnBookRequest) {
        apiService.returnBook(request.bookId, request.returnDate)
    }

    data class AddBookRequest(
        val bookId: UUID,
        val campusId: UUID,
        val metadataId: UUID,
        val signature: String
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

}