package de.cassisi.lending.book.extend

import de.cassisi.lending.book.ExtendLoanPolicyRepository
import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.StudentId

class ExtendLoanPolicyEventStoreRepository(repository: StudentEventStoreRepository) : ExtendLoanPolicyRepository, BaseStudentRepository(repository) {

    override fun getStudentById(studentId: StudentId): Student {
        return getById(studentId)
    }
}