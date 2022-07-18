package de.cassisi.lending.book


sealed interface BookEvent

data class BorrowableBookAdded(
    val bookId: BookId
): BookEvent

data class BookBorrowed(
    val bookId: BookId
) : BookEvent

data class BookReturned(
    val bookId: BookId
): BookEvent