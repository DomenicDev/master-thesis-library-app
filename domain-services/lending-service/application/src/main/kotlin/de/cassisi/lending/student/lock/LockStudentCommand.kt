package de.cassisi.lending.student.lock

import de.cassisi.lending.student.StudentId

data class LockStudentCommand(
    val studentId: StudentId
)
