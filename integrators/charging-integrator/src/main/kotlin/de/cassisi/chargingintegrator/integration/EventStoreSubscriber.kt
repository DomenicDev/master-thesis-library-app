package de.cassisi.chargingintegrator.integration

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.chargingintegrator.checkpoint.CheckpointStorage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class EventStoreSubscriber(
    private val client: EventStoreDBClient,
    private val checkpointStorage: CheckpointStorage,
    private val eventPublisher: ChargingEventPublisher,
    @Value("\${stream-prefix}") private val  streamPrefix: String) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(EventStoreSubscriber::class.java)
    private val gson = Gson()

    companion object {
        private const val LENDING_VIOLATION_OCCURRED = "lending-violation-occurred"
        private const val LENDING_VIOLATION_RESOLVED = "lending-violation-resolved"

        private const val CHECKPOINT_STREAM_NAME = "charging-integration-checkpoint"
    }

    override fun run(vararg args: String?) {
        logger.info("Subscription starting at position ${getCurrentPosition()}")
        startSubscription()
    }

    private fun startSubscription() {
        client.subscribeToAll(
            object : SubscriptionListener() {

                override fun onEvent(subscription: Subscription, event: ResolvedEvent) {
                    handleEvent(event)
                    storeCheckpoint(event)
                }

                override fun onError(subscription: Subscription, throwable: Throwable) {
                    logger.warn("Subscription was dropped due to ${throwable.message}")
                    resubscribe()
                }
            },
            getSubscriptionOptions()
        )
    }

    private fun handleEvent(event: ResolvedEvent) {
        val eventId = event.originalEvent.eventId.toString()
        val eventType = event.originalEvent.eventType
        val eventData = event.originalEvent.eventData
        val streamId = event.originalEvent.streamId
        val json = String(eventData)
        logger.info("Handle Event: $streamId $eventType $json")
        try {
            when (eventType) {
                LENDING_VIOLATION_OCCURRED -> {
                    val serializableEvent = gson.fromJson(json, SerializableLendingViolationOccurred::class.java)
                    val studentId = serializableEvent.studentId
                    publishLendingViolationOccurredEvent(streamId, eventId, studentId)
                }
                LENDING_VIOLATION_RESOLVED -> {
                    val serializableEvent = gson.fromJson(json, SerializableLendingViolationResolved::class.java)
                    val studentId = serializableEvent.studentId
                    publishLendingViolationResolvedEvent(streamId, eventId, studentId)
                }
            }

        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
    }

    private fun publishLendingViolationOccurredEvent(streamId: String, eventId: String, studentId: UUID) {
        val event = LendingViolationOccurredEvent(studentId)
        val payload = gson.toJson(event)
        publishEvent(streamId, eventId, "lending-violation-occurred", payload)
    }

    private fun publishLendingViolationResolvedEvent(streamId: String, eventId: String, studentId: UUID) {
        val event = LendingViolationResolvedEvent(studentId)
        val payload = gson.toJson(event)
        publishEvent(streamId, eventId, "lending-violation-resolved", payload)
    }

    private fun publishEvent(streamId: String, eventId: String, eventType: String, payload: String) {
        eventPublisher.sendJson(streamId, eventId, eventType, payload)
    }

    private fun storeCheckpoint(event: ResolvedEvent) {
        val position = event.originalEvent.streamRevision.valueUnsigned
        checkpointStorage.storeCheckpoint(CHECKPOINT_STREAM_NAME, position)
    }

    private fun getCurrentPosition(): Long {
        return checkpointStorage.getCurrentCheckpoint(CHECKPOINT_STREAM_NAME)
    }

    private fun resubscribe() {
        startSubscription()
    }

    private fun getSubscriptionOptions(): SubscribeToAllOptions {
        val bookStreamFilter = getSubscriptionFilter()
        val posUnsigned = getCurrentPosition()
        val position = Position(posUnsigned, posUnsigned)
        return SubscribeToAllOptions.get()
            .fromPosition(position)
            .filter(bookStreamFilter)
    }

    private fun getSubscriptionFilter(): SubscriptionFilter {
        return SubscriptionFilter.newBuilder()
            .withStreamNameRegularExpression("^${streamPrefix}")
            .build()
    }


    data class LendingViolationOccurredEvent(
        val studentId: UUID
    )

    data class LendingViolationResolvedEvent(
        val studentId: UUID
    )
}


