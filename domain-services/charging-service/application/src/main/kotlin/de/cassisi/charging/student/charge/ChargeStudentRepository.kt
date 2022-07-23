package de.cassisi.charging.student.charge

import de.cassisi.charging.common.AggregateRepository
import de.cassisi.charging.student.Student
import de.cassisi.charging.student.StudentId

interface ChargeStudentRepository : AggregateRepository<StudentId, Student>