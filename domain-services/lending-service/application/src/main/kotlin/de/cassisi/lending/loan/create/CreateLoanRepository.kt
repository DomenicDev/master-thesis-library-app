package de.cassisi.lending.loan.create

import de.cassisi.lending.common.AggregateRepository
import de.cassisi.lending.loan.Loan
import de.cassisi.lending.loan.LoanId

interface CreateLoanRepository : AggregateRepository<LoanId, Loan>