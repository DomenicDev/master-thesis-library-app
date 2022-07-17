package de.cassisi.catalogue.book

abstract class BorrowableBookException : RuntimeException()

class BookAlreadyLoanException(val id: BookId): BorrowableBookException()