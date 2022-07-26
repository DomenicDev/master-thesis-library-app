package de.cassisi.lending.student

import de.cassisi.lending.student.CreateStudentCommand
import de.cassisi.lending.student.UpdateMatriculationStatusCommand
import de.cassisi.lending.student.UpdateStudentChargesCommand
import de.cassisi.lending.student.charge.UpdateStudentCharges
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatus
import de.cassisi.lending.student.register.RegisterStudent
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val registerStudent: RegisterStudent,
    private val updateStudentCharges: UpdateStudentCharges,
    private val updateMatriculationStatus: UpdateMatriculationStatus
) {

    fun registerStudent(studentId: StudentId, status: MatriculationStatus, charges: Charges) {
        val command = CreateStudentCommand(studentId, status, charges)
        registerStudent.execute(command)
    }

    fun updateStudentCharges(command: UpdateStudentChargesCommand) {
        updateStudentCharges.execute(command)
    }

    fun updateMatriculationStatus(command: UpdateMatriculationStatusCommand) {
        updateMatriculationStatus.execute(command)
    }

}