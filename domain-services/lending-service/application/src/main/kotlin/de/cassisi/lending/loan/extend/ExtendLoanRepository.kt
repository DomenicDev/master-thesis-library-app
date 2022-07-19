package de.cassisi.lending.loan.extend

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface ExtendLoanRepository : AggregateRepository<LoanId, Loan>