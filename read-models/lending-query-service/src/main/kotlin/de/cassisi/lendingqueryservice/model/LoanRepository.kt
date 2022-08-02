package de.cassisi.lendingqueryservice.model

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanRepository : MongoRepository<LoanDocument, String> {

    /**
     * Returns all active loans
     */
    fun findByActiveIsTrue(): List<LoanDocument>

    fun findByStudentId(studentId: String): List<LoanDocument>

}