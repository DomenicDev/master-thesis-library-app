package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIQueryService
import de.cassisi.apigateway.service.BookLoanDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/lending")
class LendingController(private val apiQueryService: APIQueryService) {

    @GetMapping("/all")
    fun getAllBooksWithLoans(): ResponseEntity<List<BookLoanDTO>> {
        return ResponseEntity.ok(apiQueryService.getLoans())
    }

    @GetMapping("book")
    fun getLoansForBook(@RequestParam bookId: UUID): ResponseEntity<BookLoanDTO> {
        return ResponseEntity.ok(apiQueryService.getLoansForBook(bookId))
    }

}