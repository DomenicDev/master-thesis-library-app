package de.cassisi.lending.loan.create

import de.cassisi.lending.loan.Loan

interface CreateLoanRepository {

    fun save(loan: Loan)

}