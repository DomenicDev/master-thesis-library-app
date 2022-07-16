package de.cassisi.catalogue.common

object Versions {

    fun of(version: Long): Version {
        return SimpleVersion(version)
    }

    fun init(): Version {
        return SimpleVersion(-1)
    }

}