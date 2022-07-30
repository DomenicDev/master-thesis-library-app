package de.cassisi.apigateway.service

import java.util.*

interface APIQueryService {

    fun getCatalogue(): List<MetadataDocument>

    fun getStudentInformation(studentId: UUID): StudentDTO

    fun searchByTitle(title: String): List<SimpleMetadataDocument>

    fun getLoans(): List<BookLoanDTO>

    fun getLoansForBook(bookId: UUID): BookLoanDTO

}