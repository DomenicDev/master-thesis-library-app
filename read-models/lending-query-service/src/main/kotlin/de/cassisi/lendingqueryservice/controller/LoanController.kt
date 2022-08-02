package de.cassisi.lendingqueryservice.controller

import de.cassisi.lendingqueryservice.model.LoanDocument
import de.cassisi.lendingqueryservice.service.LoanQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/loan")
class LoanController(private val queryService: LoanQueryService) {

    @GetMapping("/all")
    fun getAll(): List<LoanDocument> {
        return queryService.getAll()
    }

    @GetMapping("/active")
    fun getActiveLoans(): List<LoanDocument> {
        return queryService.getAllActiveLoans()
    }

    @GetMapping
    fun getLoansForStudent(@RequestParam studentId: UUID): List<LoanDocument> {
        return queryService.getLoansForStudent(studentId.toString())
    }

}