package de.cassisi.lending.common

interface Version {

    fun get(): Long

    fun isNew(): Boolean

}