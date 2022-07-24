package de.cassisi.lending.lendbook

import de.cassisi.lending.loan.LoanCommand
import de.cassisi.lending.loan.LoanId

interface LendBook {

    fun execute(command: LoanCommand.OpenLoan): Result<LoanId>

}