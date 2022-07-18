package de.cassisi.lending.loan.extend

import de.cassisi.lending.loan.LoanCommand

interface ExtendLoan {

    fun execute(command: LoanCommand.ExtendLoan)

}