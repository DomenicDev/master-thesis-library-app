package de.cassisi.catalogue.loan.create

import de.cassisi.catalogue.loan.LoanCommand
import de.cassisi.catalogue.loan.LoanCreationPolicy
import de.cassisi.catalogue.loan.LoanFactory

class CreateLoanExecutor(
    private val repository: CreateLoanRepository,
    private val policy: LoanCreationPolicy
) : CreateLoan {

    override fun execute(command: LoanCommand.OpenLoan) {
        val loanAggregate = LoanFactory.create(command, policy)
        this.repository.save(loanAggregate)
    }
}