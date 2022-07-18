package de.cassisi.lending.common

data class SimpleVersion(val version: Long): Version {

    override fun get(): Long {
        return this.version
    }

    override fun isNew(): Boolean {
        return this.version == -1L
    }

}