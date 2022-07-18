package de.cassisi.lending.loan.create

import de.cassisi.lending.loan.LoanCommand

interface CreateLoan {

    fun execute(command: LoanCommand.OpenLoan)

}