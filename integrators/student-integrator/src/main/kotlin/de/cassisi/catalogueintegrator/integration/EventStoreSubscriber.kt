package de.cassisi.catalogueintegrator.integration

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.catalogueintegrator.checkpoint.CheckpointStorage
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class EventStoreSubscriber(
    private val client: EventStoreDBClient,
    private val checkpointStorage: CheckpointStorage,
    private val eventHandler: StudentIntegrationEventHandler,
    @Value("\${stream-prefix}")private val  streamPrefix: String) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(EventStoreSubscriber::class.java)
    private val gson = Gson()

    companion object {
        private const val STUDENT_ADDED = "student-added"
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"
        private const val CHECKPOINT_STREAM_NAME = "student-integrator-student-integration"
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
                STUDENT_ADDED -> {
                    eventHandler.handleJson(streamId, eventId, eventType, json)
                }
                STUDENT_MATRICULATION_CHANGED -> {
                    eventHandler.handleJson(streamId, eventId, eventType, json)
                }
            }

        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
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

}


