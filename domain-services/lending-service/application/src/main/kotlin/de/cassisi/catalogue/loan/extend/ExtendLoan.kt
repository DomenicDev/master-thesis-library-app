package de.cassisi.catalogue.loan.extend

import de.cassisi.catalogue.loan.LoanCommand

interface ExtendLoan {

    fun execute(command: LoanCommand.ExtendLoan)

}