package de.cassisi.catalogue.integration

interface CheckpointStorage {

    fun storeCheckpoint(streamName: String, streamPosition: Long)

    fun getCurrentCheckpoint(streamName: String): Long
}