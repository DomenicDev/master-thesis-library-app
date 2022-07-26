package de.cassisi.lending.book

import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface BookExtensionPolicyRepository {

    fun getStudentById(studentId: StudentId): Student

}