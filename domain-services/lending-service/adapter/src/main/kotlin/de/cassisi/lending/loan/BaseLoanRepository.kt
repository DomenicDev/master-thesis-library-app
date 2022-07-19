package de.cassisi.lending.loan

import de.cassisi.lending.common.AggregateRepository

open class BaseLoanRepository(private val repository: LoanEventStoreRepository) : AggregateRepository<LoanId, Loan> {

    override fun save(aggregate: Loan) {
        repository.saveAggregate(aggregate)
    }

    override fun getById(id: LoanId): Loan {
        return repository.loadAggregateById(id)
    }
}