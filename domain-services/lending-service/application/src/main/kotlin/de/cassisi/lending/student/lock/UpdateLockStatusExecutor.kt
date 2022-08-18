package de.cassisi.lending.student.lock


class UpdateLockStatusExecutor(private val repository: UpdateLockStatusRepository) : UpdateLockStatus {

    override fun execute(command: LockStudentCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.lockStudentForLending()
        repository.save(student)
    }

    override fun execute(command: UnlockStudentCommand) {
        val studentId = command.studentId
        val student = repository.getById(studentId)
        student.unlockStudentForLending()
        repository.save(student)
    }

}