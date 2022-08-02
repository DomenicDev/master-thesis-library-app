package de.cassisi.lendingqueryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("loans")
data class LoanDocument(
    @Id
    val loanId: String,
    @Indexed
    val bookId: String,
    @Indexed
    val studentId: String,
    val startDate: String,
    val endDate: String,
    val returnDate: String?,
    val active: Boolean,
    val extensions: Int
)
