package de.cassisi.student.student.register

import de.cassisi.student.student.Student
import de.cassisi.student.student.StudentId
import de.cassisi.student.common.AggregateRepository

interface RegisterStudentRepository : AggregateRepository<StudentId, Student>
