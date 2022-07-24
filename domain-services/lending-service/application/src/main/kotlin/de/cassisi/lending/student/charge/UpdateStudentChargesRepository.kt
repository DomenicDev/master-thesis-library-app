package de.cassisi.lending.student.charge

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface UpdateStudentChargesRepository : AggregateRepository<StudentId, Student>