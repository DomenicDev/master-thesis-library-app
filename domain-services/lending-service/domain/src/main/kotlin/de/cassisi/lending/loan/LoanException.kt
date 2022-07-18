package de.cassisi.lending.loan

sealed class LoanException : RuntimeException()

data class LoanExtensionExceeded(val loanId: LoanId) : LoanException()

data class LoanAlreadyReturned(val loanId: LoanId): LoanException()

data class LoanCreationPolicyFailed(val reason: String): LoanException()