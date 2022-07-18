package de.cassisi.lending.student.resetcharge

import de.cassisi.lending.student.BaseStudentRepository
import de.cassisi.lending.student.StudentEventStoreRepository

class ResetChargeEventStoreRepository(repository: StudentEventStoreRepository) : ResetChargesRepository, BaseStudentRepository(repository)
