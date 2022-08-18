package de.cassisi.lending.student.lock

interface UpdateLockStatus {

    fun execute(command: LockStudentCommand)

    fun execute(command: UnlockStudentCommand)

}