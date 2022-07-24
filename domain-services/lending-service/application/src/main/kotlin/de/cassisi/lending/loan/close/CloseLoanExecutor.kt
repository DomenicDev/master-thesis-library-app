package de.cassisi.lending.loan.close

import de.cassisi.lending.loan.LoanCommand

class CloseLoanExecutor(private val repository: CloseLoanRepository) : CloseLoan {

    override fun execute(command: LoanCommand.CloseLoan) {
        val loanId = command.loanId
        val loan = repository.getById(loanId)
        loan.execute(command)
        repository.save(loan)
    }
}