package de.cassisi.lending.loan.create

import de.cassisi.lending.loan.BaseLoanRepository
import de.cassisi.lending.loan.LoanEventStoreRepository

class CreateLoanEventStoreRepository(repository: LoanEventStoreRepository) : CreateLoanRepository, BaseLoanRepository(repository)