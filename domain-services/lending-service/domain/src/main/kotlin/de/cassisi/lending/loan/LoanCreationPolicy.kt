package de.cassisi.lending.loan

import de.cassisi.lending.student.StudentId

class LoanCreationPolicy(private val repository: LoanCreationStudentRepository) {

    companion object {
        private const val MAX_CHARGES = 20
    }

    fun validateLoanCreationPolicy(studentId: StudentId) {
        // load student from repository
        val student = repository.findById(studentId)

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