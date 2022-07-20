package de.cassisi.student.student

import de.cassisi.student.common.AggregateRepository

open class BaseStudentRepository(private val repository: StudentEventStoreRepository) : AggregateRepository<StudentId, Student> {
    
    override fun save(aggregate: Student) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: StudentId): Student {
        return repository.loadAggregateById(id)
    }
}