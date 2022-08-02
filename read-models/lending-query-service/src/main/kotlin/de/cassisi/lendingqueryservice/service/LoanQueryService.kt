package de.cassisi.lendingqueryservice.service

import de.cassisi.lendingqueryservice.model.LoanDocument
import de.cassisi.lendingqueryservice.model.LoanRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoanQueryService(private val repository: LoanRepository) {

    fun getAll(): List<LoanDocument> {
        return this.repository.findAll()
    }

    fun getById(loanId: String): Optional<LoanDocument> {
        return this.repository.findById(loanId)
    }

    fun getAllActiveLoans(): List<LoanDocument> {
        return this.repository.findByActiveIsTrue()
    }

    fun getLoansForStudent(studentId: String): List<LoanDocument> {
        return this.repository.findByStudentId(studentId)
    }

}