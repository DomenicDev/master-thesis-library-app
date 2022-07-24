package de.cassisi.lending.returnbook

import de.cassisi.lending.loan.LoanId

interface ReturnBook {

    fun execute(loanId: LoanId)

}