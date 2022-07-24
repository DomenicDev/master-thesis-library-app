package de.cassisi.lending.loan

import de.cassisi.lending.book.BookId
import de.cassisi.lending.loan.extend.ExtendLoan
import de.cassisi.lending.service.LoanService
import de.cassisi.lending.student.StudentId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/lending")
class LendingController(private val loanService: LoanService, private val extendLoan: ExtendLoan) {

    @PostMapping
    fun borrowBook(@RequestBody request: BorrowBookRequest): ResponseEntity<String> {
        val loanId = LoanId(UUID.randomUUID())
        val bookId = BookId(request.bookId)
        val studentId = StudentId(request.studentId)
        val startDate = LocalDate.now()
        val result = loanService.borrowBook(loanId, studentId, bookId, startDate)
        return if (result.isSuccess) ResponseEntity.ok("Loan created with id ${loanId.uuid}")
        else ResponseEntity.badRequest().body("Something went wrong")
    }

    @PostMapping("/extend")
    fun extendLoan(@RequestBody request: ExtendLoanRequest): ResponseEntity<String> {
        val loanId = LoanId(request.loanId)
        TODO("")
    }

    @PostMapping
    fun returnBook(): ResponseEntity<String> {
        TODO("")
    }

    data class BorrowBookRequest(
        val studentId: UUID,
        val bookId: UUID,
    )

    data class ExtendLoanRequest(
        val loanId: UUID
    )

}