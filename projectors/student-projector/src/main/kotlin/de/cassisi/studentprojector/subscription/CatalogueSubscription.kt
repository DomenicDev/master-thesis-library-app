package de.cassisi.studentprojector.subscription

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.studentprojector.checkpoint.CheckpointStorage
import de.cassisi.studentprojector.eventhandler.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CatalogueSubscription(
    private val client: EventStoreDBClient,
    private val checkpointStorage: CheckpointStorage,
    private val eventHandler: EventHandler
) {

    private val logger = LoggerFactory.getLogger(CatalogueSubscription::class.java)
    private val gson = Gson()

    companion object {
        // EVENT TYPES
        private const val STUDENT_ADDED = "student-added"
        private const val STUDENT_MATRICULATION_CHANGED = "student-matriculation-changed"

        // CHECKPOINT NAME
        private const val CHECKPOINT_KEY = "student-projector"
    }

    init {
        logger.info("Subscribing to event store from position ${getCurrentPosition()}...")
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


    private fun handleEvent(re: ResolvedEvent) {
        val eventId = re.originalEvent.eventId.toString()
        val eventType = re.originalEvent.eventType
        val eventData = re.originalEvent.eventData
        val streamId = re.originalEvent.streamId
        val json = String(eventData)
        logger.info("Receiving event: $streamId $eventType")
        try {
            when (eventType) {
                STUDENT_ADDED -> {
                    val event = gson.fromJson(json, SerializableStudentCreated::class.java)
                    eventHandler.handle(event)
                }
                STUDENT_MATRICULATION_CHANGED -> {
                    val event = gson.fromJson(json, SerializableStudentMatriculationChanged::class.java)
                    eventHandler.handle(event)
                }
            }
        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
    }

    private fun storeCheckpoint(event: ResolvedEvent) {
        val position = event.originalEvent.position.commitUnsigned
        checkpointStorage.storeCheckpoint(CHECKPOINT_KEY, position)
        logger.info("checkpoint stored (position: $position)")
    }

    private fun getCurrentPosition(): Long {
        return checkpointStorage.getCurrentCheckpoint(CHECKPOINT_KEY)
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
            .withEventTypeRegularExpression("^[^\\$].*") // filter out system events
            .build()
    }


}