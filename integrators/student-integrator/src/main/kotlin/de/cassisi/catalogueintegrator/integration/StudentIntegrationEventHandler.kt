package de.cassisi.catalogueintegrator.integration

import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class StudentIntegrationEventHandler(
    @Value("\${student-topic.name}") private val topicName: String,
    private val template: KafkaTemplate<String, String>) {

    private val gson = Gson()

    private fun send(streamId: String, eventId: String, eventType: String, json: String) {
        val message = MessageBuilder
            .withPayload(json)
            .setHeader(KafkaHeaders.MESSAGE_KEY, streamId)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader("eventId", eventId)
            .setHeader("eventType", eventType)
            .build()
        template.send(message)
    }

    private fun toJson(any: Any) = gson.toJson(any)

    /**
     * streamId -> message key
     * eventId -> header 'eventId'
     * eventType -> header 'eventType'
     * payload -> will be converted to json
     */
    fun handle(streamId: String, eventId: String, eventType: String, payload: Any) {
        send(streamId, eventId, eventType, toJson(payload))
    }

    fun handleJson(streamId: String, eventId: String, eventType: String, jsonPayload: String) {
        send(streamId, eventId, eventType, jsonPayload)
    }

}