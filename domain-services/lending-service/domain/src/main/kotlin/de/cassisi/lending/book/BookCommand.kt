package de.cassisi.lending.book


sealed interface BookCommand {
    data class AddBook(
        val bookId: BookId
    ): BookCommand

    data class BorrowBookCommand(
        val bookId: BookId,
    ): BookCommand

    data class ReturnBookCommand(
        val bookId: BookId
    ): BookCommand

}

