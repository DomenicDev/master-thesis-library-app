package de.cassisi.lending.loan.extend

import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface ExtendLoanRepository {

    fun findById(loanId: LoanId): Loan

    fun save(loan: Loan)

}