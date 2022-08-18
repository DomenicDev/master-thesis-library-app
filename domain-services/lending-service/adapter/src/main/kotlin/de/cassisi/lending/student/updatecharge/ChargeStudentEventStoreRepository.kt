package de.cassisi.lending.student.updatecharge

import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.lock.UpdateLockStatusRepository

class ChargeStudentEventStoreRepository(repository: StudentEventStoreRepository) : UpdateLockStatusRepository, BaseStudentRepository(repository)
