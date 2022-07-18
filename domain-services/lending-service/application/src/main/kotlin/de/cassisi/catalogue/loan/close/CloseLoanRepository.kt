package de.cassisi.catalogue.loan.close

import de.cassisi.catalogue.loan.Loan
import de.cassisi.catalogue.loan.LoanId

interface CloseLoanRepository {

    fun findById(loanId: LoanId): Loan

    fun save(loan: Loan)

}