package de.cassisi.lending.loan.close

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface CloseLoanRepository : AggregateRepository<LoanId, Loan>