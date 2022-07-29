package de.cassisi.cataloguesearchprojector.eventhandler

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch.core.IndexRequest
import de.cassisi.cataloguesearchprojector.dbmodel.MetadataDocument
import de.cassisi.cataloguesearchprojector.subscription.SerializableMetadataAdded
import org.springframework.stereotype.Component

@Component
class EventHandler(
    private val client: ElasticsearchClient
) {


    fun handle(event: SerializableMetadataAdded) {
        val document = MetadataDocument(
            event.metadataId.toString(),
            event.title,
            event.author,
            event.isbn,
            event.publisher
        )
        val request = IndexRequest.of { i: IndexRequest.Builder<MetadataDocument> ->
            i
                .index("metadata")
                .id(document.metadataId)
                .document(document)
        }
        client.index(request)
    }

}