package de.cassisi.catalogueprojector.subscription

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.catalogueprojector.eventhandler.EventHandler
import de.cassisi.catalogueprojector.checkpoint.CheckpointStorage
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
        private const val BOOK_ADDED = "book-added"
        private const val BOOK_SIGNATURE_UPDATED = "signature-updated"
        private const val CAMPUS_OPENED = "campus-opened"
        private const val METADATA_ADDED = "metadata-added"

        // CHECKPOINT NAME
        private const val CHECKPOINT_KEY = "catalogue-projector"
    }

    init {
        logger.info("Subscribing to event store...")
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
        logger.info("Handle Event: $streamId $eventType $json")
        try {
            when (eventType) {
                BOOK_ADDED -> {
                    val event = gson.fromJson(json, BookAddedToCatalogueSerializable::class.java)
                    eventHandler.handle(event)
                }
                BOOK_SIGNATURE_UPDATED -> {
                    val event = gson.fromJson(json, BookSignatureUpdatedSerializable::class.java)
                    eventHandler.handle(event)
                }
                CAMPUS_OPENED -> {
                    val event = gson.fromJson(json, CampusOpenedSerializable::class.java)
                    eventHandler.handle(event)
                }
                METADATA_ADDED -> {
                    val event = gson.fromJson(json, SerializableMetadataAdded::class.java)
                    eventHandler.handle(event)
                }
            }

        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
    }

    private fun storeCheckpoint(event: ResolvedEvent) {
        val position = event.originalEvent.streamRevision.valueUnsigned
        checkpointStorage.storeCheckpoint(CHECKPOINT_KEY, position)
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
            .withEventTypeRegularExpression("/^[^\\$].*/") // filter out system events
            .build()
    }


}