package de.cassisi.catalogue.metadata

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/metadata")
class MetadataController(private val addMetadata: AddMetadata) {

    @PostMapping
    fun addMetadata(@RequestBody request: AddMetadataRequest): ResponseEntity<String> {
        val metadataId = MetadataId(UUID.randomUUID())
        val title = Title(request.title)
        val author = Author(request.author)
        val isbn = ISBN(request.isbn)
        val publisher = Publisher(request.publisher)

        val command = AddMetadataCommand(
            metadataId,
            title,
            author,
            isbn,
            publisher
        )

        // execute command
        addMetadata.execute(command)
        return ResponseEntity.ok("Metadata added with id: ${metadataId.uuid}")
    }


    data class AddMetadataRequest(
        val title: String,
        val author: String,
        val isbn: String,
        val publisher: String
    )
}

