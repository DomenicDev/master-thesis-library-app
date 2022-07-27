package de.cassisi.chargingqueryservice.service

import de.cassisi.chargingqueryservice.model.StudentDocument
import de.cassisi.chargingqueryservice.model.StudentRepository
import org.springframework.stereotype.Service

@Service
class QueryService(private val metadataRepository: StudentRepository) {

    fun getAll(): MutableList<StudentDocument> {
        return metadataRepository.findAll()
    }

    fun getById(studentId: String): StudentDocument {
        return metadataRepository.findById(studentId).get()
    }

}