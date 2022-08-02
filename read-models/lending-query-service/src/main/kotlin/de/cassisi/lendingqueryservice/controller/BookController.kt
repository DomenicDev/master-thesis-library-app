package de.cassisi.lendingqueryservice.controller

import de.cassisi.lendingqueryservice.model.BookDocument
import de.cassisi.lendingqueryservice.service.BookQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/book")
class BookController(private val queryService: BookQueryService) {

    @GetMapping("/all")
    fun getAll(): List<BookDocument> {
        return queryService.getAll().toList()
    }

    @GetMapping
    fun getBookLoans(@RequestParam bookId: UUID): BookDocument {
        return queryService.getById(bookId.toString())
    }

}