package de.cassisi.student.student

import de.cassisi.student.student.register.RegisterStudent
import de.cassisi.student.student.updatematriculationstatus.UpdateMatriculationStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("student")
class StudentController(private val registerStudent: RegisterStudent, private val updateMatriculationStatus: UpdateMatriculationStatus) {

    @PostMapping
    fun createStudent(@RequestBody request: RegisterStudentRequest): ResponseEntity<String> {
        val studentId = StudentId(request.studentId)
        val name = Name(request.forename, request.lastname)
        val email = Email(request.email)
        val matriculationNumber = MatriculationNumber(request.matriculationNumber)
        val matriculated = MatriculationStatus(request.matriculated)

        val registerCommand = CreateStudentCommand(
            studentId,
            name,
            email,
            matriculationNumber,
            matriculated
        )

        registerStudent.execute(registerCommand)
        return ResponseEntity.ok("Student registered with id: ${studentId.uuid}")
    }

    @PutMapping
    fun updateMatriculationStatus(@RequestBody request: UpdateMatriculationStatusRequest): ResponseEntity<String> {
        val studentId = StudentId(request.studentId)
        val matriculated = MatriculationStatus(request.matriculated)

        val updateCommand = UpdateMatriculationStatusCommand(
            studentId,
            matriculated
        )

        updateMatriculationStatus.execute(updateCommand)
        return ResponseEntity.ok("Matriculation status of student with id ${studentId.uuid} updated.")
    }

}