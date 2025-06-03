package com.example.elastic.data.loader.util;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;

import com.example.elastic.data.loader.elasticsearch.document.ClientFullProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticBulkIndexer {

    private final ElasticsearchClient elasticsearchClient;

    public void bulkIndexClients(List<ClientFullProfile> clientProfiles) {
        if (clientProfiles == null || clientProfiles.isEmpty()) {
            log.info("No client profiles to index.");
            return;
        }

        List<BulkOperation> operations = clientProfiles.stream()
            .map(profile -> BulkOperation.of(op -> op
                .index(idx -> idx
                    .index("client-data")
                    .id(String.valueOf(profile.getClientId()))
                    .document(profile)
                )
            ))
            .collect(Collectors.toList());

        try {
            BulkRequest request = new BulkRequest.Builder()
                .operations(operations)
                .build();

            BulkResponse response = elasticsearchClient.bulk(request);

            if (response.errors()) {
                log.warn("Bulk index had errors: {}", response.items().stream()
                    .filter(item -> item.error() != null)
                    .map(item -> item.error().reason())
                    .collect(Collectors.joining(", ")));
            } else {
                log.info("Bulk index successful for {} documents", clientProfiles.size());
            }

        } catch (IOException e) {
            log.error("Failed to bulk index clients to Elasticsearch", e);
            throw new RuntimeException("Elasticsearch bulk indexing failed", e);
        }
    }
}
