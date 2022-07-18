package de.cassisi.lending.student.charge

import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.StudentEventStoreRepository

class ChargeStudentEventStoreRepository(repository: StudentEventStoreRepository) : ChargeStudentRepository, BaseStudentRepository(repository)
