package de.cassisi.charging.student.charge

import de.cassisi.charging.student.BaseStudentRepository
import de.cassisi.charging.student.StudentEventStoreRepository

class ChargeStudentEventStoreRepository(repository: StudentEventStoreRepository) : ChargeStudentRepository, BaseStudentRepository(repository)