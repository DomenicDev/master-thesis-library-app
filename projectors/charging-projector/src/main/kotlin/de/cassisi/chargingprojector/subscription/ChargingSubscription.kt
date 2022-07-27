package de.cassisi.chargingprojector.subscription

import com.eventstore.dbclient.*
import com.google.gson.Gson
import de.cassisi.chargingprojector.checkpoint.CheckpointStorage
import de.cassisi.chargingprojector.eventhandler.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ChargingSubscription(
    private val client: EventStoreDBClient,
    private val checkpointStorage: CheckpointStorage,
    private val eventHandler: EventHandler
) {

    private val logger = LoggerFactory.getLogger(ChargingSubscription::class.java)
    private val gson = Gson()

    companion object {
        // EVENT TYPES
        private const val STUDENT_CHARGE_ACCOUNT_CREATED = "student-charge-account-created"
        private const val STUDENT_CHARGED = "student-charged"
        private const val STUDENT_CHARGES_PAID = "student-charges-paid"

        // CHECKPOINT NAME
        private const val CHECKPOINT_KEY = "charging-projector"
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
                STUDENT_CHARGE_ACCOUNT_CREATED -> {
                    val event = gson.fromJson(json, SerializableStudentChargeAccountCreated::class.java)
                    eventHandler.handle(event)
                }
                STUDENT_CHARGED -> {
                    val event = gson.fromJson(json, SerializableStudentCharged::class.java)
                    eventHandler.handle(event)
                }
                STUDENT_CHARGES_PAID -> {
                    val event = gson.fromJson(json, SerializableStudentChargesPaid::class.java)
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