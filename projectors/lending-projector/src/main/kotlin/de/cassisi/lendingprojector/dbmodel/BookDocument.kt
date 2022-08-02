package de.cassisi.lendingprojector.dbmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "books")
data class BookDocument(
    @Id
    val bookId: String,
    val loans: List<Loan>
)

data class Loan(
    val loanId: String,
    @Indexed
    val studentId: String,
    val startDate: String,
    val endDate: String,
    val returnDate: String?,
    val active: Boolean,
    val extensions: Int
)