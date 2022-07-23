package de.cassisi.charging.student.register

import de.cassisi.charging.student.BaseStudentRepository
import de.cassisi.charging.student.StudentEventStoreRepository

class RegisterStudentEventStoreRepository(repository: StudentEventStoreRepository) : CreateChargeAccountRepository, BaseStudentRepository(repository)