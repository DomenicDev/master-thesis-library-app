package de.cassisi.lending.student.lock

import de.cassisi.lending.student.StudentId

data class UnlockStudentCommand(
    val studentId: StudentId
)
