package de.cassisi.lendingprojector.eventhandler


import de.cassisi.lendingprojector.dbmodel.*
import de.cassisi.lendingprojector.subscription.*
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val bookRepository: BookRepository,
    private val loanRepository: LoanRepository
) {
    fun handle(event: SerializableBookAdded) {
        val bookDocument = BookDocument(
            event.bookId.toString(),
            available = true,
            reserved = false,
            currentLoan = null,
            currentReservation = null
        )
        bookRepository.save(bookDocument)
    }

    fun handle(event: SerializableBookBorrowed) {
        updateBookDocument(event)
        updateLoanDocument(event)
    }

    private fun updateBookDocument(event: SerializableBookBorrowed) {
        val oldDocument = bookRepository.findById(event.bookId.toString()).get()
        val newCurrentLoan = Loan(
            event.loanId.toString(),
            event.studentId.toString(),
            event.startDate.toString(),
            event.endDate.toString()
        )
        val updated = oldDocument.copy(available = false, currentLoan = newCurrentLoan)
        bookRepository.save(updated)
    }

    private fun updateLoanDocument(event: SerializableBookBorrowed) {
        val loanDocument = LoanDocument(
            event.loanId.toString(),
            event.bookId.toString(),
            event.studentId.toString(),
            event.startDate.toString(),
            event.endDate.toString(),
            null,
            true,
            0
        )
        loanRepository.save(loanDocument)
    }

    fun handle(event: SerializableBookReturned) {
        val currentBookDocument = bookRepository.findById(event.bookId.toString()).get()
        val updatedBookDocument = currentBookDocument.copy(
            available = true,
            currentLoan = null
        )
        bookRepository.save(updatedBookDocument)

        // update loan document as well
        val currentLoanDocument = loanRepository.findById(event.loanId.toString()).get()
        val updatedLoanDocument = currentLoanDocument.copy(active = false, returnDate = event.returnDate.toString())
        loanRepository.save(updatedLoanDocument)
    }

    fun handle(event: SerializableLoanExtended) {
        updateBookDocument(event)
        updateLoanDocument(event)
    }

    private fun updateBookDocument(event: SerializableLoanExtended) {
        val currentBookDocument = bookRepository.findById(event.bookId.toString()).get()
        val updatedLoan = currentBookDocument.currentLoan?.copy(endDate = event.endDate.toString())
        val updatedBookDocument = currentBookDocument.copy(currentLoan = updatedLoan)
        bookRepository.save(updatedBookDocument)
    }

    private fun updateLoanDocument(event: SerializableLoanExtended) {
        val currentDocument = loanRepository.findById(event.loanId.toString()).get()
        val updatedDocument = currentDocument.copy(returnDate = event.endDate.toString(), extensions = event.numberOfExtensions)
        loanRepository.save(updatedDocument)
    }

    fun handle(event: SerializableBookReserved) {
        val currentBookDocument = bookRepository.findById(event.bookId.toString()).get()
        val updatedDocument = currentBookDocument.copy(
            reserved = true,
            currentReservation = Reservation(
                event.reservedBy.toString(),
                event.reservationDate.toString(),
                event.expirationDate.toString())
        )
        bookRepository.save(updatedDocument)
    }

    fun handle(event: SerializableReservationCleared) {
        val currentDocument = bookRepository.findById(event.bookId.toString()).get()
        val updatedDocument = currentDocument.copy(
            reserved = false,
            currentReservation = null
        )
        bookRepository.save(updatedDocument)
    }

}