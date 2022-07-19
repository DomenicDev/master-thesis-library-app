package de.cassisi.lending.loan.extend

import de.cassisi.lending.loan.BaseLoanRepository
import de.cassisi.lending.loan.LoanEventStoreRepository

class ExtendLoanEventStoreRepository(repository: LoanEventStoreRepository) : ExtendLoanRepository, BaseLoanRepository(repository)