package de.cassisi.chargingintegrator.checkpoint

interface CheckpointStorage {

    fun storeCheckpoint(streamName: String, streamPosition: Long)

    fun getCurrentCheckpoint(streamName: String): Long

}