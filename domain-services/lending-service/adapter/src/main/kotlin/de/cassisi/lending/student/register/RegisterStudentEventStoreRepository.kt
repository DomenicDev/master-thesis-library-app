package de.cassisi.lending.student.register

import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.StudentEventStoreRepository

class RegisterStudentEventStoreRepository(repository: StudentEventStoreRepository) : RegisterStudentRepository, BaseStudentRepository(repository)