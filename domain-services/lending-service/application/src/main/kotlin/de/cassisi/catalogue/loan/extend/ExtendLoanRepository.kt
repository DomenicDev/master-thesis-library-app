package de.cassisi.catalogue.loan.extend

import de.cassisi.catalogue.loan.Loan
import de.cassisi.catalogue.loan.LoanId

interface ExtendLoanRepository {

    fun findById(loanId: LoanId): Loan

    fun save(loan: Loan)

}