package de.cassisi.catalogueintegrator.config

import de.cassisi.catalogueintegrator.integration.CheckpointStorage
import de.cassisi.catalogueintegrator.integration.RedisCheckpointStorage
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis

@Configuration
class CheckpointStorageConfig {

    @Bean
    fun jedis(
        @Value("\${checkpoint-storage.redis.host}") host: String,
        @Value("\${checkpoint-storage.redis.port}") port: Int,
    ): Jedis {
        val jedis = Jedis(host, port)
        jedis.auth("Y6R8zL83El")
        return jedis
    }

    @Bean
    fun checkpointStorage(jedis: Jedis): CheckpointStorage {
        return RedisCheckpointStorage(jedis)
    }

}