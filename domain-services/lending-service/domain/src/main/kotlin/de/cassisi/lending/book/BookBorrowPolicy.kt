package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId

class BookBorrowPolicy(private val repository: BookBorrowPolicyRepository) {

    companion object {
        private const val MAX_CHARGES = 20
    }

    fun validateIfStudentIsAllowedToBorrow(studentId: StudentId) {
        // load student from repository
        val student = repository.findStudentById(studentId)

        // read fields
        val matriculated = student.getMatriculationStatus().status
        val charges = student.getCharges().amount

        // validate policy rules:
        // 1) the student must be matriculated
        if (!matriculated) {
            throw LoanCreationPolicyFailed("Student with id $studentId is not matriculated")
        }
        // 2) the student charges must not be higher than 20
        if (charges > MAX_CHARGES) {
            throw LoanCreationPolicyFailed("The charges of the specified student are too high: $charges")
        }

    }



}