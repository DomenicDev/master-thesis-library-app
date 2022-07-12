package de.cassisi.catalogue.book

import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.metadata.MetadataId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("book")
class BookController(private val bookCommandHandler: BookCommandHandler) {

    @PostMapping
    fun createBook(@RequestBody request: AddBookRequest) {
        val bookId = BookId(UUID.randomUUID())
        val campusId = CampusId(request.campusId)
        val metadataId = MetadataId(request.metadataId)
        val signature = Signature(request.signature)

        val createCommand = BookCommand.AddBook(
            bookId, metadataId, campusId, signature
        )

        bookCommandHandler.handle(createCommand)
    }

}