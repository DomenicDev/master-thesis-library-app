package de.cassisi.catalogue.config

import com.eventstore.dbclient.EventStoreDBClient
import de.cassisi.catalogue.metadata.AddMetadata
import de.cassisi.catalogue.metadata.AddMetadataExecutor
import de.cassisi.catalogue.metadata.AddMetadataRepository
import de.cassisi.catalogue.metadata.MetadataEventStoreRepository
import de.cassisi.catalogue.metadata.add.AddMetadataEventStoreRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetadataConfig {

    @Bean
    fun metadataEventStoreRepository(client: EventStoreDBClient): MetadataEventStoreRepository {
        return MetadataEventStoreRepository(client)
    }

    @Bean
    fun addMetadataRepository(repository: MetadataEventStoreRepository): AddMetadataRepository {
        return AddMetadataEventStoreRepository(repository)
    }

    @Bean
    fun addMetadata(repository: AddMetadataRepository): AddMetadata {
        return AddMetadataExecutor(repository)
    }

}