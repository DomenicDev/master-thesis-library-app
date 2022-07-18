package de.cassisi.lending.loan.create

import de.cassisi.lending.loan.LoanCommand
import de.cassisi.lending.loan.LoanCreationPolicy
import de.cassisi.lending.loan.LoanFactory

class CreateLoanExecutor(
    private val repository: CreateLoanRepository,
    private val policy: LoanCreationPolicy
) : CreateLoan {

    override fun execute(command: LoanCommand.OpenLoan) {
        val loanAggregate = LoanFactory.create(command, policy)
        this.repository.save(loanAggregate)
    }
}