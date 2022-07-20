package de.cassisi.catalogue.config

import de.cassisi.catalogue.integration.CheckpointStorage
import de.cassisi.catalogue.integration.RedisCheckpointStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis

@Configuration
class CheckpointStorageConfiguration {

    @Bean
    fun jedis(
        @Value("\${checkpoint-store.redis.host}") host: String,
        @Value("\${checkpoint-store.redis.port}") port: Int,
    ): Jedis {
        val jedis = Jedis(host, port)
        jedis.auth("default", "redispw")
        return jedis
    }

    @Bean
    fun checkpointStorage(jedis: Jedis): CheckpointStorage {
        return RedisCheckpointStorage(jedis)
    }

}