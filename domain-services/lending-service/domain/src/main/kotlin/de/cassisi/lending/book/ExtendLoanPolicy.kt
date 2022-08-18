package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId

class ExtendLoanPolicy(private val repository: ExtendLoanPolicyRepository) {

    companion object {
        private const val MAX_CHARGES = 20
    }

    fun validateIfStudentIsAllowedToExtendBook(studentId: StudentId): Result<Unit> {
        val student = repository.getStudentById(studentId)
        if (student.isLocked()) {
            return Result.failure(NotAllowedToExtendLoan("Charges exceeded"))
        }
        if (!student.isMatriculated()) {
            return Result.failure(NotAllowedToExtendLoan("Student must be matriculated."))
        }
        return Result.success(Unit)
    }

}