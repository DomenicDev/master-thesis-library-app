package de.cassisi.lending.loan.create

import de.cassisi.lending.loan.LoanCreationPolicyRepository
import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

class LoanCreationPolicyEventStoreRepository(private val studentRepository: BaseStudentRepository) : LoanCreationPolicyRepository {

    override fun findStudentById(studentId: StudentId): Student {
        return this.studentRepository.getById(studentId)
    }

}