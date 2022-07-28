package de.cassisi.catalogue.book

import de.cassisi.catalogue.campus.CampusId
import de.cassisi.catalogue.metadata.MetadataId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("book")
class BookController(private val bookCommandHandler: BookCommandHandler, private val repo: BookRepository) {

    @PostMapping
    fun createBook(@RequestBody request: AddBookRequest) {
        val bookId = BookId(request.bookId)
        val campusId = CampusId(request.campusId)
        val metadataId = MetadataId(request.metadataId)
        val signature = Signature(request.signature)

        val createCommand = BookCommand.AddBook(
            bookId, metadataId, campusId, signature
        )

        bookCommandHandler.handle(createCommand)
        println(bookId)
    }

    @PutMapping
    fun updateSignature(@RequestBody request: UpdateSignatureRequest): ResponseEntity<String> {
        val command = BookCommand.UpdateBookSignature(
            BookId(request.bookId),
            Signature(request.signature)
        )

        bookCommandHandler.handle(command)
        return ResponseEntity.ok("Updated!")
    }

    @GetMapping
    fun get(@RequestParam id: UUID): ResponseEntity<String> {
        val book = repo.getById(BookId(id))
        println(book.getSignature())
        println(book.getCampusId())
        println(book.getMetadataId())
        println()
        return ResponseEntity.ok(book.toString())
    }


    data class AddBookRequest(
        val bookId: UUID,
        val campusId: UUID,
        val metadataId: UUID,
        val signature: String
    )


    data class UpdateSignatureRequest(val bookId: UUID, val signature: String)


}