package de.cassisi.lending.book

sealed class BookException : RuntimeException()

data class BookAlreadyLoanException(val bookId: BookId): BookException()

data class NoMoreExtensionsPossible(val extensions: Int): BookException()

data class LoanCreationPolicyFailed(val reason: String): BookException()

data class NotAllowedToExtendLoan(val reason: String): BookException()