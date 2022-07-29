package de.cassisi.apigateway.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientQueryServiceConfig {

    @Bean
    fun catalogueQueryService(@Value("\${query-service.catalogue}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun studentQueryService(@Value("\${query-service.student}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun lendingQueryService(@Value("\${query-service.lending}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun chargingQueryService(@Value("\${query-service.charging}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

    @Bean
    fun catalogueSearchQueryService(@Value("\${query-service.catalogue-search}") url: String): WebClient {
        return WebClient.builder().baseUrl(url).build()
    }

}