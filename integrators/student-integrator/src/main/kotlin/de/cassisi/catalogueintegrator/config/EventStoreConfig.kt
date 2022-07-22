package de.cassisi.catalogueintegrator.config

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventStoreConfig {

    @Bean
    fun client(@Value("\${eventstore.connection-string}") connectionString: String): EventStoreDBClient {
        val settings = EventStoreDBConnectionString.parse(connectionString)
        return EventStoreDBClient.create(settings)
    }

}