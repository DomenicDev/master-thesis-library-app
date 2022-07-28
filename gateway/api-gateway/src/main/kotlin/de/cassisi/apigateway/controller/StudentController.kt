package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/student")
class StudentController(private val apiService: APIService) {

    @PostMapping
    fun addStudent(@RequestBody request: AddStudentRequest): ResponseEntity<UUID> {
        val studentId = UUID.randomUUID()
        apiService.addStudent(studentId, request.matriculationNumber, request.forename, request.lastname, request.email, request.matriculated)
        return ResponseEntity.ok(studentId)
    }

    @PostMapping
    fun chargeStudent(@RequestBody request: ChargeStudentRequest) {
        apiService.chargeStudent(request.studentId, request.amount)
    }

    @PostMapping
    fun clearCharges(@RequestBody request: ClearChargesRequest) {
        apiService.clearCharges(request.studentId)
    }

    @PostMapping
    fun changeMatriculationStatus(@RequestBody request: ChangeMatriculationStatus) {
        apiService.changeMatriculationStatus(request.studentId, request.matriculated)
    }

    data class AddStudentRequest(
        val matriculationNumber: Int,
        val forename: String,
        val lastname: String,
        val email: String,
        val matriculated: Boolean,
    )

    data class ChargeStudentRequest(
        val studentId: UUID,
        val amount: Int
    )

    data class ClearChargesRequest(
        val studentId: UUID
    )

    data class ChangeMatriculationStatus(
        val studentId: UUID,
        val matriculated: Boolean
    )

}