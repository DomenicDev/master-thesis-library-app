package de.cassisi.lending.book

import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface BorrowBookPolicyRepository {

    fun findStudentById(studentId: StudentId): Student

}