package de.cassisi.chargingintegrator.checkpoint

import redis.clients.jedis.Jedis

class RedisCheckpointStorage(private val jedis: Jedis) : CheckpointStorage {

    override fun storeCheckpoint(streamName: String, streamPosition: Long) {
        jedis.set(streamName, "$streamPosition")
    }

    override fun getCurrentCheckpoint(streamName: String): Long {
        if (jedis.exists(streamName)) {
            return jedis.get(streamName).toLong()
        }
        return 0 // zero means from start
    }
}
