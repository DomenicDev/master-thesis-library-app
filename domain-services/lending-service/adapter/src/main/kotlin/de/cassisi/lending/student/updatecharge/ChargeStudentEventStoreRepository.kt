package de.cassisi.lending.student.updatecharge

import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.StudentEventStoreRepository
import de.cassisi.lending.student.charge.UpdateStudentChargesRepository

class ChargeStudentEventStoreRepository(repository: StudentEventStoreRepository) : UpdateStudentChargesRepository, BaseStudentRepository(repository)
