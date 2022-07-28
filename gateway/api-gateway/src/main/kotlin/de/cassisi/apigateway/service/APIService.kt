package de.cassisi.apigateway.service

import java.time.LocalDate
import java.util.*

interface APIService {

    fun addMetadata(
        metadataId: UUID,
        title: String,
        author: String,
        isbn: String,
        publisher: String
    )

    fun addCampus(
        campusId: UUID,
        location: String
    )

    fun addBook(
        bookId: UUID,
        campusId: UUID,
        metadataId: UUID,
        signature: String
    )

    fun addStudent(
        studentId: UUID,
        matriculationNumber: Int,
        forename: String,
        lastname: String,
        email: String,
        matriculated: Boolean,
    )

    fun borrowBook(
        bookId: UUID,
        studentId: UUID,
        startDate: LocalDate
    )

    fun extendLoan(
        bookId: UUID,
    )

    fun returnBook(
        bookId: UUID,
        returnDate: LocalDate
    )

    fun chargeStudent(
        studentId: UUID,
        amount: Int
    )

    fun clearCharges(
        studentId: UUID
    )

    fun changeMatriculationStatus(
        studentId: UUID,
        matriculated: Boolean
    )
}