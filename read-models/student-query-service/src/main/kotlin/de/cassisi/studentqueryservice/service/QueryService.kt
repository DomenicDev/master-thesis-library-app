package de.cassisi.studentqueryservice.service

import de.cassisi.studentqueryservice.model.StudentDocument
import de.cassisi.studentqueryservice.model.StudentRepository
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