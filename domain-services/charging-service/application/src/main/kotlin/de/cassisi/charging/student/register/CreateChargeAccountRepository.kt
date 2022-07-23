package de.cassisi.charging.student.register

import de.cassisi.charging.student.Student
import de.cassisi.charging.student.StudentId
import de.cassisi.charging.common.AggregateRepository

interface CreateChargeAccountRepository : AggregateRepository<StudentId, Student>
