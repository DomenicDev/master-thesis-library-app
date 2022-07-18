package de.cassisi.catalogue.loan.extend

import de.cassisi.catalogue.loan.LoanCommand

class ExtendLoanExecutor(private val repository: ExtendLoanRepository) : ExtendLoan {

    override fun execute(command: LoanCommand.ExtendLoan) {
        // load loan from repository
        val loanId = command.loanId
        val loan = repository.findById(loanId)

        // execute loan extension
        loan.execute(command)

        // save aggregate
        repository.save(loan)
    }
}