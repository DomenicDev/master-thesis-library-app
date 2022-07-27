package de.cassisi.studentprojector.dbmodel

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository : MongoRepository<StudentDocument, String>