package de.cassisi.lendingqueryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "books")
data class BookDocument(
    @Id
    val bookId: String,
    val available: Boolean,
    val reserved: Boolean,
    val currentReservation: Reservation?,
    val currentLoan: Loan?
)

data class Reservation(
    val reservedBy: String,
    val reservationDate: String,
    val expirationDate: String
)

data class Loan(
    val loanId: String,
    val studentId: String,
    val startDate: String,
    val endDate: String,
)