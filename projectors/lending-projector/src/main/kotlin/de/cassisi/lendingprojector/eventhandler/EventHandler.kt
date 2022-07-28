package de.cassisi.lendingprojector.eventhandler


import de.cassisi.lendingprojector.dbmodel.BookDocument
import de.cassisi.lendingprojector.dbmodel.BookRepository
import de.cassisi.lendingprojector.dbmodel.Loan
import de.cassisi.lendingprojector.subscription.*
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val bookRepository: BookRepository
) {
    fun handle(event: SerializableBookAdded) {
        val bookDocument = BookDocument(
            event.bookId.toString(),
            emptyList()
        )
        bookRepository.save(bookDocument)
    }

    fun handle(event: SerializableBookBorrowed) {
        val oldDocument = bookRepository.findById(event.bookId.toString()).get()
        val loan = Loan(
            event.loanId.toString(),
            event.studentId.toString(),
            event.startDate.toString(),
            event.endDate.toString(),
            null,
            true
        )
        val updated = oldDocument.copy(loans = oldDocument.loans + loan)
        bookRepository.save(updated)
    }

    fun handle(event: SerializableBookReturned) {
        val currentDocument = bookRepository.findById(event.bookId.toString()).get()
        val currentLoan = currentDocument.loans.first { it.loanId == event.loanId.toString() }
        val updatedLoan = currentLoan.copy(returnDate = event.returnDate.toString(), active = false)
        val newLoans = currentDocument.loans.minus(currentLoan).plus(updatedLoan)
        val updatedDocument = currentDocument.copy(loans = newLoans)
        bookRepository.save(updatedDocument)
    }

    fun handle(event: SerializableLoanExtended) {
        val currentDocument = bookRepository.findById(event.bookId.toString()).get()
        val currentLoan = currentDocument.loans.first { it.loanId == event.loanId.toString() }
        val updatedLoan = currentLoan.copy(endDate = event.endDate.toString())
        val newLoans = currentDocument.loans.minus(currentLoan).plus(updatedLoan)
        val updatedDocument = currentDocument.copy(loans = newLoans)
        bookRepository.save(updatedDocument)
    }

}