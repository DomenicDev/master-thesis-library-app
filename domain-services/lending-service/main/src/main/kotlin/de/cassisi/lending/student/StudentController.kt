package de.cassisi.lending.student

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/student")
class StudentController(private val service: StudentService) {

    @PostMapping
    fun addStudent(@RequestBody request: AddStudentRequest) {
        val studentId = StudentId(request.studentId)
        val matriculationStatus = MatriculationStatus(request.matriculated)
        val charges = Charges(request.charges)
        this.service.registerStudent(studentId, matriculationStatus, charges)
    }

    data class AddStudentRequest(
        val studentId: UUID,
        val matriculated: Boolean,
        val charges: Int
    )
}