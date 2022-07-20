package de.cassisi.student.common

object Versions {

    fun of(version: Long): Version {
        return SimpleVersion(version)
    }

    fun init(): Version {
        return SimpleVersion(-1)
    }

}