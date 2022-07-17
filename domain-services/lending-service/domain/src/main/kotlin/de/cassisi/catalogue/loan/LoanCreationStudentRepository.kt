package de.cassisi.catalogue.loan

import de.cassisi.catalogue.student.Student
import de.cassisi.catalogue.student.StudentId

interface LoanCreationStudentRepository {

    fun findById(studentId: StudentId): Student

}