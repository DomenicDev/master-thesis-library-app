package de.cassisi.catalogue.loan.close

import de.cassisi.catalogue.loan.LoanCommand

interface CloseLoan {

    fun execute(command: LoanCommand.ReturnBook)

}