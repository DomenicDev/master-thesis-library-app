package de.cassisi.catalogueintegrator.integration

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CatalogueIntegrationEventHandler(
    @Value("\${book-topic.name}") private val bookTopicName: String,
    private val template: KafkaTemplate<String, String>) {

    fun handleBookAdded(streamId: String, json: String) {
        template.send(bookTopicName, streamId, json)
    }

}