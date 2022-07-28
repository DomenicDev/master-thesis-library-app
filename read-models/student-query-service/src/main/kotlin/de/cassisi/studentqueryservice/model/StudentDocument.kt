package de.cassisi.studentqueryservice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "students")
data class StudentDocument(
    @Id
    val studentId: String,
    val matriculationNumber: Int,
    val forename: String,
    val lastname: String,
    val email: String,
    val matriculated: Boolean
)