package de.cassisi.lending.book.extend

import de.cassisi.lending.book.ExtendLoanPolicy

class ExtendLoanExecutor(private val repository: ExtendLoanRepository, private val policy: ExtendLoanPolicy) : ExtendLoan {

    override fun execute(command: ExtendLoanCommand) {
        val bookId = command.bookId
        val book = repository.getById(bookId)
        book.extendCurrentLoan(policy)
        repository.save(book)
    }
}