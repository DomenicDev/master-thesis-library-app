package de.cassisi.apigateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientServiceConfig {

    @Bean
    fun catalogueService(@Value("\${service.catalogue}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun studentService(@Value("\${service.student}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun lendingService(@Value("\${service.lending}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun chargingService(@Value("\${service.charging}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

}