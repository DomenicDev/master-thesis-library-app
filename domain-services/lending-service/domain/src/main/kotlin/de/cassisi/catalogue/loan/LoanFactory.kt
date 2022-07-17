package de.cassisi.catalogue.loan

import de.cassisi.catalogue.common.Version
import de.cassisi.catalogue.common.Versions

object LoanFactory {

    fun create(command: LoanCommand.OpenLoan, policy: LoanCreationPolicy): Loan {
        // check if student is allowed to create a new loan
        policy.validateLoanCreationPolicy(command.studentId)
        val aggregate = LoanAggregate(command.loanId, Versions.init())
        val event = LoanCreated(
            command.loanId,
            command.studentId,
            command.bookId,
            command.startDate,
            command.startDate.plusWeeks(4)
        )
        aggregate.registerEvent(event)
        return aggregate
    }

    fun empty(loanId: LoanId, version: Version): Loan {
        return LoanAggregate(loanId, version)
    }
}