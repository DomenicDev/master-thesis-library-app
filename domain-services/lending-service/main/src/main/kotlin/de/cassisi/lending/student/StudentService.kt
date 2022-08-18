package de.cassisi.lending.student

import de.cassisi.lending.student.lock.LockStudentCommand
import de.cassisi.lending.student.lock.UnlockStudentCommand
import de.cassisi.lending.student.lock.UpdateLockStatus
import de.cassisi.lending.student.matriculation.UpdateMatriculationStatus
import de.cassisi.lending.student.register.RegisterStudent
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val registerStudent: RegisterStudent,
    private val updateLockStatus: UpdateLockStatus,
    private val updateMatriculationStatus: UpdateMatriculationStatus
) {

    fun registerStudent(studentId: StudentId, status: MatriculationStatus, lockStatus: LockStatus) {
        val command = CreateStudentCommand(studentId, status, lockStatus)
        registerStudent.execute(command)
    }

    fun lockStudent(command: LockStudentCommand) {
        updateLockStatus.execute(command)
    }

    fun unlockStudent(command: UnlockStudentCommand) {
        updateLockStatus.execute(command)
    }

    fun updateMatriculationStatus(command: UpdateMatriculationStatusCommand) {
        updateMatriculationStatus.execute(command)
    }

}