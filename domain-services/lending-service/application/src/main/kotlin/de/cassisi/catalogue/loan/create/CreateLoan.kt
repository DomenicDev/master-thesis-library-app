package de.cassisi.catalogue.loan.create

import de.cassisi.catalogue.loan.LoanCommand

interface CreateLoan {

    fun execute(command: LoanCommand.OpenLoan)

}