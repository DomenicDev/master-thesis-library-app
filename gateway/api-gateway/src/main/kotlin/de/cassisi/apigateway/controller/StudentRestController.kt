package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIQueryService
import de.cassisi.apigateway.service.APIService
import de.cassisi.apigateway.service.StudentDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/student")
class StudentRestController(private val apiService: APIService, private val apiQueryService: APIQueryService) {

    @PostMapping
    fun addStudent(@RequestBody request: AddStudentRequest): ResponseEntity<UUID> {
        val studentId = UUID.randomUUID()
        apiService.addStudent(studentId, request.matriculationNumber, request.forename, request.lastname, request.email, request.matriculated)
        return ResponseEntity.ok(studentId)
    }

    @PostMapping("/charge")
    fun chargeStudent(@RequestBody request: ChargeStudentRequest) {
        apiService.chargeStudent(request.studentId, request.amount)
    }

    @PostMapping("/clear")
    fun clearCharges(@RequestBody request: ClearChargesRequest) {
        apiService.clearCharges(request.studentId)
    }

    @PostMapping("/status")
    fun changeMatriculationStatus(@RequestBody request: ChangeMatriculationStatus) {
        apiService.changeMatriculationStatus(request.studentId, request.matriculated)
    }

    @GetMapping
    fun getStudentDTO(@RequestParam studentId: UUID): ResponseEntity<StudentDTO> {
        return ResponseEntity.ok(apiQueryService.getStudentInformation(studentId))
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