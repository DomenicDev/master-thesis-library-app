package de.cassisi.charging.student

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/student")
class StudentChargingController(private val studentService: StudentService) {

    @PostMapping
    fun createStudent(@RequestBody request: CreateChargeAccountRequest): ResponseEntity<String> {
        val studentId = StudentId(UUID.randomUUID())
        val registerCommand = CreateChargingAccountCommand(studentId)
        studentService.createChargingAccount(registerCommand)
        return ResponseEntity.ok("Charge account created for student with id: ${studentId.uuid}")
    }

    @PostMapping("/charge")
    fun chargeStudent(@RequestBody request: ChargeStudentRequest): ResponseEntity<String> {
        val studentId = StudentId(request.studentId)
        val charges = Charges(request.amount)

        val updateCommand = ChargeStudentCommand(
            studentId,
            charges
        )

        studentService.chargeStudent(updateCommand)
        return ResponseEntity.ok("Student with id ${studentId.uuid} charged.")
    }

    @PostMapping("/clear")
    fun clearCharges(@RequestBody request: ClearChargesRequest): ResponseEntity<String> {
        val studentId = StudentId(request.studentId)
        val command = ClearChargesCommand(studentId)
        studentService.clearCharges(command)
        return ResponseEntity.ok("Charges of student (${studentId.uuid} cleared.")
    }

    // REQUEST DTOs

    data class CreateChargeAccountRequest(
        val studentId: UUID
    )

    data class ChargeStudentRequest(
        val studentId: UUID,
        val amount: Int
    )

    data class ClearChargesRequest(
        val studentId: UUID
    )

}