package de.cassisi.chargingintegrator.config

import de.cassisi.chargingintegrator.checkpoint.CheckpointStorage
import de.cassisi.chargingintegrator.checkpoint.RedisCheckpointStorage
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
        @Value("\${checkpoint-storage.redis.password}") password: String,
    ): Jedis {
        val jedis = Jedis(host, port)
        jedis.auth(password)
        return jedis
    }

    @Bean
    fun checkpointStorage(jedis: Jedis): CheckpointStorage {
        return RedisCheckpointStorage(jedis)
    }

}