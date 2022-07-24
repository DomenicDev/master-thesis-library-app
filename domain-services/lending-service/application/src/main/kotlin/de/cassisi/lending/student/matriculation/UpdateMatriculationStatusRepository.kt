package de.cassisi.lending.student.matriculation

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface UpdateMatriculationStatusRepository : AggregateRepository<StudentId, Student>