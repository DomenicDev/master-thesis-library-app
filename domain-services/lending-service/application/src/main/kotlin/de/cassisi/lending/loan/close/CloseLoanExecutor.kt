package de.cassisi.lending.loan.close

import de.cassisi.lending.loan.LoanCommand

class CloseLoanExecutor(private val repository: CloseLoanRepository) : CloseLoan {

    override fun execute(command: LoanCommand.ReturnBook) {
        val loanId = command.loanId
        val loan = repository.findById(loanId)
        loan.execute(command)
        repository.save(loan)
    }
}