package de.cassisi.lending.book.borrow

import de.cassisi.lending.book.BorrowBookPolicyRepository
import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.StudentId

class BorrowBookPolicyEventStoreRepository(repository: StudentEventStoreRepository) : BorrowBookPolicyRepository, BaseStudentRepository(repository) {

    override fun findStudentById(studentId: StudentId): Student {
        return getById(studentId)
    }
}