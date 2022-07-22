package de.cassisi.catalogueintegrator.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaConfig {

    @Bean
    fun studentTopic(@Value("\${student-topic.name}") bookTopicName: String, @Value("\${student-topic.partitions}") partitions: Int,): NewTopic {
        return NewTopic(bookTopicName, partitions, 1)
    }

}