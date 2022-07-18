package de.cassisi.catalogue.loan.create

import de.cassisi.catalogue.loan.Loan

interface CreateLoanRepository {

    fun save(loan: Loan)

}