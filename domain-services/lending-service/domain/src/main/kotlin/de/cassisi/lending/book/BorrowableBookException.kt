package de.cassisi.lending.book

abstract class BorrowableBookException : RuntimeException()

class BookAlreadyLoanException(val id: BookId): BorrowableBookException()