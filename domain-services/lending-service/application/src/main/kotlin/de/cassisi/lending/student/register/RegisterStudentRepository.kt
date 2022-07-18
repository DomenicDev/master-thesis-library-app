package de.cassisi.lending.student.register

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface RegisterStudentRepository : AggregateRepository<StudentId, Student>
