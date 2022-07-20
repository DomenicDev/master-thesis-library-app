package de.cassisi.student.student.register

import de.cassisi.student.student.BaseStudentRepository
import de.cassisi.student.student.StudentEventStoreRepository

class RegisterStudentEventStoreRepository(repository: de.cassisi.student.student.StudentEventStoreRepository) : RegisterStudentRepository, de.cassisi.student.student.BaseStudentRepository(repository)