package de.cassisi.lendingprojector.dbmodel

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<BookDocument, String>