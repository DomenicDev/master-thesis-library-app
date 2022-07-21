package de.cassisi.catalogueintegrator.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun booksTopic(@Value("\${book-topic.name}") bookTopicName: String, @Value("\${book-topic.partitions}") partitions: Int,): NewTopic {
        return NewTopic(bookTopicName, partitions, 1)
    }

}