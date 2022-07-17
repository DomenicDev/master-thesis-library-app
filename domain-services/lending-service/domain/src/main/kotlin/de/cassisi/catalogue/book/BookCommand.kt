package de.cassisi.catalogue.book


sealed interface BookCommand

data class BorrowBookCommand(
    val bookId: BookId,
)

data class ReturnBookCommand(
    val bookId: BookId
)