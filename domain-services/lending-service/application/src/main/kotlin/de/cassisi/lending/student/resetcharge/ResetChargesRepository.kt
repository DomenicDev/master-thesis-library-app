package de.cassisi.lending.student.resetcharge

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface ResetChargesRepository : AggregateRepository<StudentId, Student>