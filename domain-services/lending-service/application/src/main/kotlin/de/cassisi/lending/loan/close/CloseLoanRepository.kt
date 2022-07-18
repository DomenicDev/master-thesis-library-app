package de.cassisi.lending.loan.close

import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface CloseLoanRepository {

    fun findById(loanId: LoanId): Loan

    fun save(loan: Loan)

}