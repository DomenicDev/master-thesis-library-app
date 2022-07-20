package de.cassisi.lending.common

interface Aggregate<ID> {

    fun getId(): ID

    fun getVersion(): Version

}