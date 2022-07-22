package de.cassisi.catalogue.integration

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.lending.common.GsonBuilder.fromJson
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CatalogueIntegration(
    private val catalogueEventStoreDBClient: EventStoreDBClient,
    private val eventHandler: CatalogueIntegrationEventHandler,
    private val checkpointStorage: CheckpointStorage,

    @Value("\${integration.catalogue.book-stream-prefix}")
    private val bookPrefix: String
) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(CatalogueIntegration::class.java)

    companion object {
        private const val ET_BOOK_ADDED = "book-added"

        private const val CHECKPOINT_STREAM_NAME = "lending-service-book-integration"
    }

    override fun run(vararg args: String?) {
        startSubscription()
        logger.info("start subscribing from position ${getCurrentPosition()}")
    }

    private fun startSubscription() {
        catalogueEventStoreDBClient.subscribeToAll(
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
            bookSubscriptionOptions()
        )
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

    private fun handleEvent(event: ResolvedEvent) {
        val eventType = event.originalEvent.eventType
        val eventData = event.originalEvent.eventData
        val json = String(eventData)
        try {
            if (eventType == ET_BOOK_ADDED) {
                eventHandler.handleBookAdded(Gson().fromJson(json))
            }
        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
    }

    private fun bookSubscriptionOptions(): SubscribeToAllOptions {
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