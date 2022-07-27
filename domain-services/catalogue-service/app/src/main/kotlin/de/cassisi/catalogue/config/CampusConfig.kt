package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.campus.CampusCommandHandler
import de.cassisi.catalogue.campus.CampusEventStoreRepository
import de.cassisi.catalogue.campus.CampusRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CampusConfig {

    @Bean
    fun campusRepository(client: EventStoreDBClient): CampusRepository {
        return CampusEventStoreRepository(client)
    }

    @Bean
    fun campusCommandHandler(repository: CampusRepository): CampusCommandHandler {
        return CampusCommandHandler(repository)
    }

}