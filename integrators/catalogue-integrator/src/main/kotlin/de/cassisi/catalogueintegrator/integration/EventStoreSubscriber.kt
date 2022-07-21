package de.cassisi.catalogueintegrator.integration

import com.eventstore.dbclient.*
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
@Component
class EventStoreSubscriber(
    private val client: EventStoreDBClient,
    private val checkpointStorage: CheckpointStorage,
    private val eventHandler: CatalogueIntegrationEventHandler,
    @Value("\${book-prefix}")private val  bookPrefix: String) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(EventStoreSubscriber::class.java)

    companion object {
        private const val ET_BOOK_ADDED = "book-added"
        private const val CHECKPOINT_STREAM_NAME = "lending-service-book-integration"
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
            getBookSubscriptionOptions()
        )
    }

    private fun handleEvent(event: ResolvedEvent) {
        val eventType = event.originalEvent.eventType
        val eventData = event.originalEvent.eventData
        val streamId = event.originalEvent.streamId
        val json = String(eventData)
        try {
            if (eventType == ET_BOOK_ADDED) {
                eventHandler.handleBookAdded(streamId, json)
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


    private fun getBookSubscriptionOptions(): SubscribeToAllOptions {
        val bookStreamFilter = bookSubscriptionFilter()
        val posUnsigned = getCurrentPosition()
        val position = Position(posUnsigned, posUnsigned)
        return SubscribeToAllOptions.get()
            .fromPosition(position)
            .filter(bookStreamFilter)
    }

    private fun bookSubscriptionFilter(): SubscriptionFilter {
        return SubscriptionFilter.newBuilder()
            .withStreamNameRegularExpression("^${bookPrefix}")
            .build()
    }

}

