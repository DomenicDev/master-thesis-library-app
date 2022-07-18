package de.cassisi.lending.common

import com.eventstore.dbclient.EventData
import com.eventstore.dbclient.EventDataBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonBuilder {

    private val gson = Gson()

    fun buildEventData(eventType: String, event: Any): EventData {
        val json = gson.toJson(event)
        val bytes = json.encodeToByteArray()
        return EventDataBuilder.json(eventType, bytes).build()
    }

    // thanks to: https://stackoverflow.com/questions/33381384/how-to-use-typetoken-generics-with-gson-in-kotlin
    inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object: TypeToken<T>() {}.type)

    fun gson(): Gson = this.gson
}