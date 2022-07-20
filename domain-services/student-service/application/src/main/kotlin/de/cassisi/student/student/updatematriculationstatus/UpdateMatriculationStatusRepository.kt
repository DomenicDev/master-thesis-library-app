package de.cassisi.student.student.updatematriculationstatus

import de.cassisi.student.common.AggregateRepository
import de.cassisi.student.student.Student
import de.cassisi.student.student.StudentId

interface UpdateMatriculationStatusRepository : AggregateRepository<StudentId, Student>