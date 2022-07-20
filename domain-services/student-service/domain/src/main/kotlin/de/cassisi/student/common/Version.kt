package de.cassisi.student.common

interface Version {

    fun get(): Long

    fun isNew(): Boolean

}