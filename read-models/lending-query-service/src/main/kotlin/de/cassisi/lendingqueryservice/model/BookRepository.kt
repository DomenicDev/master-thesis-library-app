package de.cassisi.lendingqueryservice.model

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : MongoRepository<BookDocument, String>