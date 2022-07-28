package de.cassisi.apigateway.controller

import de.cassisi.apigateway.service.APIService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/catalogue")
class BookController(private val apiService: APIService) {

    @PostMapping("/metadata")
    fun addMetadata(@RequestBody request: AddMetadataRequest): ResponseEntity<String> {
        val metadataId = UUID.randomUUID()
        apiService.addMetadata(metadataId, request.title, request.author, request.isbn, request.publisher)
        return ResponseEntity.ok("$metadataId")
    }

    data class AddMetadataRequest(
        val title: String,
        val author: String,
        val isbn: String,
        val publisher: String
    )

}