package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.book.BookCommandHandler
import de.cassisi.catalogue.book.BookEventStoreRepository
import de.cassisi.catalogue.book.BookRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BookConfig {

    @Bean
    fun bookRepository(client: EventStoreDBClient): BookRepository {
        return BookEventStoreRepository(client)
    }

    @Bean
    fun bookCommandHandler(repository: BookRepository): BookCommandHandler {
        return BookCommandHandler(repository)
    }

}