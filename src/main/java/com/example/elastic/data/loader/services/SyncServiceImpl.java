package com.example.elastic.data.loader.services;

import java.io.IOException;
import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.elastic.data.loader.elasticsearch.document.ClientFullProfile;
import com.example.elastic.data.loader.mysql.entities.ClientDetails;
import com.example.elastic.data.loader.mysql.repositories.ClientDetailsRepository;
import com.example.elastic.data.loader.oracle.entities.ClientsAssetsLiabilities;
import com.example.elastic.data.loader.oracle.repositories.ClientsAssetsLiabilitiesRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import jakarta.persistence.EntityNotFoundException;

/**
 * SyncServiceImpl combines client details and assets/liabilities and syncs to
 * Elasticsearch.
 * All exceptions are handled and rethrown as RuntimeException with context.
 */
@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    @Autowired
    private ClientDetailsRepository clientDetailsRepository;
    
    @Autowired
    private ClientsAssetsLiabilitiesRepository clientsAssetsLiabilitiesRepository;

    @Override
    public void syncClient(Integer clientId) {
        try {

            ClientFullProfile profile = merge(
                    clientDetailsRepository.findById(clientId)
                            .orElseThrow(() -> new EntityNotFoundException("Client not found")),
                    clientsAssetsLiabilitiesRepository.findByClientId(clientId));

            Map<Integer, ClientFullProfile> mergedData = new HashMap<>();
            mergedData.put(clientId, profile);

            elasticsearchClient.index(i -> i
                    .index("client-data")
                    .id(String.valueOf(clientId))
                    .document(profile));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to sync client data to Elasticsearch for clientId: " + clientId, e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error during sync for clientId: " + clientId, e);
        }
    }

    @Override
    public void syncAllClients() {
        try {
            List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
            List<ClientsAssetsLiabilities> assetsLiabilitiesList = clientsAssetsLiabilitiesRepository.findAll();

            Map<Integer, ClientFullProfile> mergedData = new HashMap<>();

            for (ClientDetails clientDetails : clientDetailsList) {
                ClientsAssetsLiabilities assetsLiabilities = assetsLiabilitiesList.stream()
                        .filter(al -> al.getClientId().equals(clientDetails.getClientId()))
                        .findFirst()
                        .orElse(new ClientsAssetsLiabilities());

                ClientFullProfile profile = merge(clientDetails, assetsLiabilities);
                mergedData.put(clientDetails.getClientId(), profile);
            }

            for (Map.Entry<Integer, ClientFullProfile> entry : mergedData.entrySet()) {
                elasticsearchClient.index(i -> i
                        .index("client-data")
                        .id(String.valueOf(entry.getKey()))
                        .document(entry.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to sync all clients data to Elasticsearch", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error during sync of all clients", e);
        }
    }

    private ClientFullProfile merge(ClientDetails clientDetails, ClientsAssetsLiabilities assetsLiabilities) {
        ClientFullProfile profile = new ClientFullProfile();
        BeanUtils.copyProperties(clientDetails, profile);
        BeanUtils.copyProperties(assetsLiabilities, profile);
        return profile;
    }
}
