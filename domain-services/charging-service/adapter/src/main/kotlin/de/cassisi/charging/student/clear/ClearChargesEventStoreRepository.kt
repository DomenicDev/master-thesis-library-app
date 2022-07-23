package de.cassisi.charging.student.clear

import de.cassisi.charging.student.BaseStudentRepository
import de.cassisi.charging.student.StudentEventStoreRepository

class ClearChargesEventStoreRepository(repository: StudentEventStoreRepository) : ClearChargesRepository, BaseStudentRepository(repository)