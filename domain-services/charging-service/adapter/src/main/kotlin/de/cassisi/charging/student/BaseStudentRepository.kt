package de.cassisi.charging.student

import de.cassisi.charging.common.AggregateRepository

open class BaseStudentRepository(private val repository: StudentEventStoreRepository) : AggregateRepository<StudentId, Student> {
    
    override fun save(aggregate: Student) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: StudentId): Student {
        return repository.loadAggregateById(id)
    }
}