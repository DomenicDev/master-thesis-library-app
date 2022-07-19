package de.cassisi.lending.loan.close

import de.cassisi.lending.loan.BaseLoanRepository
import de.cassisi.lending.loan.LoanEventStoreRepository

class CloseLoanEventStoreRepository(repository: LoanEventStoreRepository) : CloseLoanRepository, BaseLoanRepository(repository)