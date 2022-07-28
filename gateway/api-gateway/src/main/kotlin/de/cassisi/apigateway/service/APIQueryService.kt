package de.cassisi.apigateway.service

import java.util.*

interface APIQueryService {

    fun getCatalogue(): List<MetadataDocument>

    fun getStudentInformation(studentId: UUID): StudentDTO

}