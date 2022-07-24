package de.cassisi.lending.loan.close

import de.cassisi.lending.loan.LoanCommand

interface CloseLoan {

    fun execute(command: LoanCommand.CloseLoan)

}