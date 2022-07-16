package de.cassisi.catalogue.loan

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.student.StudentId
import java.time.LocalDate

class LoanState {

    lateinit var studentId: StudentId
    lateinit var bookId: BookId
    lateinit var startDate: LocalDate
    lateinit var endDate: LocalDate
    lateinit var returnDate: LocalDate
    var numberOfExtensions: Int = 0
    var active: Boolean = true
    var returned: Boolean = false


    fun handle(event: LoanCreated) {
        this.studentId = event.studentId
        this.bookId = event.bookId
        this.startDate = event.startDate
        this.endDate = event.endDate
        this.numberOfExtensions = 0
        this.active = true
        this.returned = false
    }

    fun handle(event: LoanExtended) {
        this.startDate = event.startDate
        this.endDate = event.endDate
        this.numberOfExtensions = event.extensions
    }

    fun handle(event: LoanReturned) {
        this.returnDate = event.returnDate
        this.returned = true
        this.active = false
    }

}