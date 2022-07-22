package de.cassisi.catalogue.integration

import com.google.gson.Gson
import de.cassisi.lending.book.BookId
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import java.util.*

@Component
class CatalogueKafkaIntegration(private val eventHandler: CatalogueIntegrationEventHandler) {

    companion object {
        private val logger = LoggerFactory.getLogger(CatalogueKafkaIntegration::class.java)

        // EVENT TYPES
        private const val BOOK_ADDED = "book-added"
    }

    private val gson = Gson()

    @KafkaListener(topics = ["books"])
    fun processMessage(
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) key: String,
        @Header("eventId") eventId: String,
        @Header("eventType") eventType: String,
        @Payload payload: String) {
        logger.info("received message with key: $key and payload: $payload")
        try {
            when (eventType) {
                BOOK_ADDED -> {
                    val eventPayload = gson.fromJson(payload, BookAddedIE::class.java)
                    val bookId = BookId(eventPayload.bookId)
                    eventHandler.handleBookAdded(bookId)
                }
            }
        } catch (e: Exception) {
            logger.warn("Error while processing message with $key Error ${e.message}")
        }
    }


    data class BookAddedIE(
        val bookId: UUID, // payload
    )
}