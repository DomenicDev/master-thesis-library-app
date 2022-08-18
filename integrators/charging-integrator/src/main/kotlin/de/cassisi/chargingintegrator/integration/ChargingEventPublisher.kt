package de.cassisi.chargingintegrator.integration

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class ChargingEventPublisher(
    @Value("\${charging-topic.name}") private val topicName: String,
    private val template: KafkaTemplate<String, String>) {


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

    fun sendJson(streamId: String, eventId: String, eventType: String, jsonPayload: String) {
        send(streamId, eventId, eventType, jsonPayload)
    }

}