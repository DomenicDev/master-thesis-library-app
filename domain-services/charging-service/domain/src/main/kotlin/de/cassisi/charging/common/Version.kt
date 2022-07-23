package de.cassisi.charging.common

interface Version {

    fun get(): Long

    fun isNew(): Boolean

}