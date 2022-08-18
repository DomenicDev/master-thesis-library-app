package de.cassisi.lending.student.lock

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.student.Student
import de.cassisi.lending.student.StudentId

interface UpdateLockStatusRepository : AggregateRepository<StudentId, Student>