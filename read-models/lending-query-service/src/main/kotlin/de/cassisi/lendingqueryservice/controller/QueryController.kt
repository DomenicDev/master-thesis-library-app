package de.cassisi.lendingqueryservice.controller

import de.cassisi.lendingqueryservice.model.BookDocument
import de.cassisi.lendingqueryservice.service.QueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class QueryController(private val queryService: QueryService) {

    @GetMapping("/all")
    fun getAll(): List<BookDocument> {
        return queryService.getAll().toList()
    }

    @GetMapping
    fun getBookLoans(@RequestParam bookId: UUID): BookDocument {
        return queryService.getById(bookId.toString())
    }

}