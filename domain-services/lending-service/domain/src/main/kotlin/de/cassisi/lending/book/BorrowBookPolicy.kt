package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId

class BorrowBookPolicy(private val repository: BorrowBookPolicyRepository) {

    fun validateIfStudentIsAllowedToBorrow(studentId: StudentId): Result<Unit> {
        // load student from repository
        val student = repository.findStudentById(studentId)

        // read fields
        val matriculated = student.getMatriculationStatus().status

        // validate policy rules:
        // 1) the student must be matriculated
        if (!matriculated) {
            return Result.failure(LoanCreationPolicyFailed("Student with id $studentId is not matriculated"))
        }
        // 2) the student charges must not be higher than 20
        if (student.isLocked()) {
            return Result.failure(LoanCreationPolicyFailed("The student with id $studentId is locked for lending."))
        }
        return Result.success(Unit)
    }

}