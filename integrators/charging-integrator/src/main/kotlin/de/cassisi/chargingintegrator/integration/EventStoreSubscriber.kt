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
    private val eventHandler: StudentIntegrationEventHandler,
    @Value("\${stream-prefix}")private val  streamPrefix: String) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(EventStoreSubscriber::class.java)
    private val gson = Gson()

    companion object {
        private const val STUDENT_CHARGE_ACCOUNT_CREATED = "student-charge-account-created"
        private const val STUDENT_CHARGED = "student-charged"
        private const val STUDENT_CHARGES_PAID = "student-charges-paid"

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
                STUDENT_CHARGE_ACCOUNT_CREATED -> {
                    val createdEvent = gson.fromJson(eventType, SerializableStudentChargeAccountCreated::class.java)
                    val studentId = createdEvent.id
                    val charges = createdEvent.charges
                    sendIntegrationEvent(streamId, eventId, studentId, charges)
                }
                STUDENT_CHARGED -> {
                    val chargedEvent = gson.fromJson(eventType, SerializableStudentCharged::class.java)
                    val studentId = chargedEvent.studentId
                    val charges = chargedEvent.currentCharges
                    sendIntegrationEvent(streamId, eventId, studentId, charges)
                }
                STUDENT_CHARGES_PAID -> {
                    val paidEvent = gson.fromJson(eventType, SerializableStudentChargesPaid::class.java)
                    val studentId = paidEvent.studentId
                    val charges = paidEvent.currentCharges
                    sendIntegrationEvent(streamId, eventId, studentId, charges)
                }
            }

        } catch (e: Exception) {
            logger.warn("event could not be processed due to ${e.message}")
        }
    }

    private fun sendIntegrationEvent(streamId: String, eventId: String, studentId: UUID, charges: Int) {
        val integrationEvent = SimpleChargingIntegrationEvent(studentId, charges)
        val json = gson.toJson(integrationEvent)
        eventHandler.sendJson(streamId, eventId, "student-charges-changed", json)
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

    data class SimpleChargingIntegrationEvent(
        val studentId: UUID,
        val currentCharges: Int
    )

}


