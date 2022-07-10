package de.cassisi.catalogue.common

interface Version {

    fun get(): Long

    fun isNew(): Boolean

}