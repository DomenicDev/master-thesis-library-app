package de.cassisi.chargingprojector.dbmodel

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "students")
data class StudentDocument(
    @Id
    val studentId: String,
    val charges: Int
)