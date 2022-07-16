package de.cassisi.catalogue.common

interface Aggregate<ID> {

    fun getId(): ID

    fun getVersion(): Version

}