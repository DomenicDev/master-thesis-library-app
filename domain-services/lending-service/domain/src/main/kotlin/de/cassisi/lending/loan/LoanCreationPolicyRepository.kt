package de.cassisi.lending.loan

import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface LoanCreationPolicyRepository {

    fun findStudentById(studentId: StudentId): Student

}