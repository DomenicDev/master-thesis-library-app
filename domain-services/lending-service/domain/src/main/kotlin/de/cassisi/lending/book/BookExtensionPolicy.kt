package de.cassisi.lending.book

import de.cassisi.lending.student.StudentId

class BookExtensionPolicy(private val repository: BookExtensionPolicyRepository) {

    companion object {
        private const val MAX_CHARGES = 20
    }

    fun validateIfStudentIsAllowedToExtendBook(studentId: StudentId) {
        val student = repository.getStudentById(studentId)
        if (student.getCharges().amount > MAX_CHARGES) {
            throw NotAllowedToExtendLoan("Charges exceeded")
        }
        if (!student.isMatriculated()) {
            throw NotAllowedToExtendLoan("Student must be matriculated.")
        }
    }

}