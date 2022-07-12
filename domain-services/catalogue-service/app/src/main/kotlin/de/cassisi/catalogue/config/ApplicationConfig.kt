package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import com.eventstore.dbclient.EventStoreDBConnectionString
import de.cassisi.catalogue.book.BookCommandHandler
import de.cassisi.catalogue.book.BookEventStoreRepository
import de.cassisi.catalogue.book.BookRepository
import de.cassisi.catalogue.campus.CampusCommandHandler
import de.cassisi.catalogue.campus.CampusEventStoreRepository
import de.cassisi.catalogue.campus.CampusRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    @Bean
    fun eventStoreDBClient(@Value("\${eventstore.connection-string}") connectionString: String): EventStoreDBClient {
        val settings = EventStoreDBConnectionString.parse(connectionString)
        return EventStoreDBClient.create(settings)
    }

    @Bean
    fun bookRepository(client: EventStoreDBClient): BookRepository {
        return BookEventStoreRepository(client)
    }

    @Bean
    fun bookCommandHandler(repository: BookRepository): BookCommandHandler {
        return BookCommandHandler(repository)
    }

    @Bean
    fun campusRepository(client: EventStoreDBClient): CampusRepository {
        return CampusEventStoreRepository(client)
    }

    @Bean
    fun campusCommandHandler(repository: CampusRepository): CampusCommandHandler {
        return CampusCommandHandler(repository)
    }
}