package de.cassisi.apigateway.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDate
import java.util.*

@Service
class APIServiceRestImpl(
    private val catalogueService: WebClient,
    private val studentService: WebClient,
    private val lendingService: WebClient,
    private val chargingService: WebClient,
) : APIService {

    override fun addMetadata(metadataId: UUID, title: String, author: String, isbn: String, publisher: String) {
        catalogueService.post()
            .uri("/metadata/")
            .bodyValue(AddMetadataRequest(metadataId, title, author, isbn, publisher))
            .retrieve()
            .bodyToMono<String>()
            .block()
    }

    data class AddMetadataRequest(
        val metadataId: UUID,
        val title: String,
        val author: String,
        val isbn: String,
        val publisher: String
    )

    override fun addCampus(campusId: UUID, location: String) {
        catalogueService.post()
            .uri("/campus")
            .bodyValue(AddCampusRequest(campusId, location))
            .retrieve()
            .toBodilessEntity()
            .block()
    }

    data class AddCampusRequest(
        val campusId: UUID,
        val campusLocation: String
    )

    override fun addBook(bookId: UUID, campusId: UUID, metadataId: UUID, signature: String) {
        // add to catalogue
        catalogueService.post()
            .uri("/book")
            .bodyValue(AddBookRequest(bookId, campusId, metadataId, signature))
            .retrieve()
            .toBodilessEntity()
            .block()

        // add to lending
        lendingService.post()
            .uri("/book")
            .bodyValue(AddBookRequestLending(bookId))
            .retrieve().toBodilessEntity().block()
    }

    data class AddBookRequest(
        val bookId: UUID,
        val campusId: UUID,
        val metadataId: UUID,
        val signature: String
    )

    data class AddBookRequestLending(
        val bookId: UUID
    )

    override fun addStudent(
        studentId: UUID,
        matriculationNumber: Int,
        forename: String,
        lastname: String,
        email: String,
        matriculated: Boolean
    ) {
        // add to lending service
        lendingService.post()
            .uri("/student")
            .bodyValue(AddStudentRequestLending(studentId, matriculated, 0))
            .retrieve().toBodilessEntity().block()

        // add to student service
        studentService.post()
            .uri("/student")
            .bodyValue(RegisterStudentRequest(studentId, forename, lastname, email, matriculationNumber, matriculated))
            .retrieve()
            .toBodilessEntity()
            .block()

        // add to charges
        chargingService.post()
            .uri("/student")
            .bodyValue(CreateChargeAccountRequest(studentId))
            .retrieve().toBodilessEntity().block()
    }

    data class RegisterStudentRequest(
        val studentId: UUID,
        val forename: String,
        val lastname: String,
        val email: String,
        val matriculationNumber: Int,
        val matriculated: Boolean,
    )

    data class AddStudentRequestLending(
        val studentId: UUID,
        val matriculated: Boolean,
        val charges: Int
    )

    data class CreateChargeAccountRequest(
        val studentId: UUID
    )

    override fun borrowBook(bookId: UUID, studentId: UUID, startDate: LocalDate) {
        lendingService.post()
            .uri("/book/borrow")
            .bodyValue(BorrowBookRequest(bookId, studentId, startDate))
            .retrieve().toBodilessEntity().block()
    }

    data class BorrowBookRequest(
        val bookId: UUID,
        val studentId: UUID,
        val startDate: LocalDate
    )

    override fun extendLoan(bookId: UUID) {
        lendingService.post()
            .uri("/book/extend")
            .bodyValue(ExtendLoanRequest(bookId))
            .retrieve().toBodilessEntity().block()
    }

    data class ExtendLoanRequest(
        val bookId: UUID
    )

    override fun returnBook(bookId: UUID, returnDate: LocalDate) {
        lendingService.post()
            .uri("/book/return")
            .bodyValue(ReturnBookRequest(bookId, returnDate))
            .retrieve().toBodilessEntity().block()
    }

    data class ReturnBookRequest(
        val bookId: UUID,
        val returnDate: LocalDate
    )

    override fun chargeStudent(studentId: UUID, amount: Int) {
        chargingService.post()
            .uri("/student/charge")
            .bodyValue(ChargeStudentRequest(studentId, amount))
            .retrieve().toBodilessEntity().block()
    }

    data class ChargeStudentRequest(
        val studentId: UUID,
        val amount: Int
    )

    override fun clearCharges(studentId: UUID) {
        chargingService.post()
            .uri("student/clear")
            .bodyValue(ClearChargesRequest(studentId))
    }

    data class ClearChargesRequest(
        val studentId: UUID
    )

}