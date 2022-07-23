package de.cassisi.charging.student.clear

import de.cassisi.charging.common.AggregateRepository
import de.cassisi.charging.student.Student
import de.cassisi.charging.student.StudentId

interface ClearChargesRepository : AggregateRepository<StudentId, Student>