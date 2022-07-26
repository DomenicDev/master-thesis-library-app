package de.cassisi.lending.book

import de.cassisi.lending.common.EventSourcedAggregate
import de.cassisi.lending.student.StudentId
import java.time.LocalDate

sealed interface Book: EventSourcedAggregate<BookId, BookEvent> {

    fun isAvailableForLoan(): Boolean

    fun borrowBook(studentId: StudentId, startDate: LocalDate, policy: BorrowBookPolicy)

    fun extendCurrentLoan(policy: ExtendLoanPolicy)

    fun returnBook(returnDate: LocalDate)

}