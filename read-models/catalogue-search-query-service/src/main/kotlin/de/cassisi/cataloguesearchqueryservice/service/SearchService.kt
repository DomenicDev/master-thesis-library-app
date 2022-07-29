package de.cassisi.cataloguesearchqueryservice.service

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery
import co.elastic.clients.elasticsearch._types.query_dsl.Query
import co.elastic.clients.elasticsearch.core.SearchRequest
import co.elastic.clients.elasticsearch.core.SearchResponse
import de.cassisi.cataloguesearchqueryservice.dbmodel.MetadataDocument
import org.springframework.stereotype.Service


@Service
class SearchService(private val client: ElasticsearchClient) {

    fun searchTitle(title: String): List<MetadataDocument> {
        val response: SearchResponse<MetadataDocument> = client.search(
            { s: SearchRequest.Builder ->
                s
                    .index("metadata")
                    .query { q: Query.Builder ->
                        q
                            .match { t: MatchQuery.Builder ->
                                t
                                    .field("title")
                                    .query(title)
                            }
                    }
            },
            MetadataDocument::class.java
        )
        return response.hits().hits().map { it.source()!! }.toList()
    }

}