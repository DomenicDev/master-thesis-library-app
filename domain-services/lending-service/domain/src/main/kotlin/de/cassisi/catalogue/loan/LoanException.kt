package de.cassisi.catalogue.loan

sealed class LoanException : RuntimeException()

data class LoanExtensionExceeded(val loanId: LoanId) : LoanException()

data class LoanAlreadyReturned(val loanId: LoanId): LoanException()