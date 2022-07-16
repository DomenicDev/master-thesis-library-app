package de.cassisi.catalogue.loan

import de.cassisi.catalogue.book.BookId
import de.cassisi.catalogue.common.EventSourcedAggregate
import de.cassisi.catalogue.student.StudentId
import java.time.LocalDate

interface Loan : EventSourcedAggregate<LoanId, LoanEvent> {

    fun getStudentId(): StudentId

    fun getBookId(): BookId

    fun getStartDate(): LocalDate

    fun getEndDate(): LocalDate

    fun getReturnDate(): LocalDate

    fun getExtensions(): Int

    fun isActive(): Boolean

    fun isReturned(): Boolean

    fun execute(command: LoanCommand.ExtendLoan)

    fun execute(command: LoanCommand.ReturnBook)

}