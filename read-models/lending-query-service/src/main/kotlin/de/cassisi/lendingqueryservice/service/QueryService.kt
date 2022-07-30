package de.cassisi.lendingqueryservice.service

import de.cassisi.lendingqueryservice.model.BookDocument
import de.cassisi.lendingqueryservice.model.BookRepository
import org.springframework.stereotype.Service

@Service
class QueryService(private val bookRepository: BookRepository) {

    fun getAll(): MutableList<BookDocument> {
        return bookRepository.findAll()
    }

    fun getById(bookId: String): BookDocument {
        return bookRepository.findById(bookId).get()
    }

}