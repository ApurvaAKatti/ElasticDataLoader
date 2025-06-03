package com.example.elastic.data.loader.scheduler;

import com.example.elastic.data.loader.elasticsearch.document.ClientFullProfile;
import com.example.elastic.data.loader.mysql.entities.ClientDetails;
import com.example.elastic.data.loader.mysql.repositories.ClientDetailsRepository;
import com.example.elastic.data.loader.oracle.entities.ClientsAssetsLiabilities;
import com.example.elastic.data.loader.oracle.repositories.ClientsAssetsLiabilitiesRepository;
import com.example.elastic.data.loader.util.ElasticBulkIndexer;
import com.example.elastic.data.loader.util.HashUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ElasticSyncScheduler {

    @Autowired
    private ClientDetailsRepository clientDetailsRepo;

    @Autowired
    private ClientsAssetsLiabilitiesRepository clientsAssetsLiabilitiesRepo;
    
    @Autowired
    private ElasticBulkIndexer elasticBulkIndexer;

    @Scheduled(fixedRate = 30000)
    // @Transactional
    public void runDeltaSync() {
        try{
            log.info("Running delta sync job...");
            syncAllChangedClients();
            log.info("Delta sync job completed successfully.");
        } catch (Exception e) {
            log.error("Error during syncAllClients: ", e);
            throw new RuntimeException("Failed to sync all clients", e);
        }
        
    }


    private void syncAllChangedClients() {
            List<ClientFullProfile> profilesToBeUpdated =  new ArrayList<>();
            List<ClientDetails> clientDetailsToBeUpdated = new ArrayList<>();
            List<ClientsAssetsLiabilities> assetsToBeUpdated =  new ArrayList<>();
            List<ClientDetails> clientDetails = clientDetailsRepo.findAll();
            List<ClientsAssetsLiabilities> clientsAssetsLiabilities = clientsAssetsLiabilitiesRepo.findAll();

            if (clientDetails.isEmpty() || clientsAssetsLiabilities.isEmpty()) {
                log.info("No clients to sync.");
                return;
            }

            clientDetails.forEach(client -> {
                
                Integer clientId = client.getClientId();
                List<ClientsAssetsLiabilities> assetsLiabilities = clientsAssetsLiabilities.stream().filter(clientAssets -> clientAssets.getClientId().equals(clientId)).collect(Collectors.toList());
                if(assetsLiabilities.isEmpty()) {
                    log.warn("No assets/liabilities found for client ID: {}", clientId);
                    return;
                }
                ClientsAssetsLiabilities assets = assetsLiabilities.get(0); // Assuming one-to-one mapping
                        
                ClientFullProfile profile = new ClientFullProfile();
                BeanUtils.copyProperties(client, profile);
                BeanUtils.copyProperties(assets, profile);

                String newHash = HashUtil.sha256Hash(profile.toString());
                String mysqlHash = client.getSyncState();
                String oracleHash = assets.getSyncState();

                if (!newHash.equals(mysqlHash) || !newHash.equals(oracleHash)) {
                    profilesToBeUpdated.add(profile);

                    client.setSyncState(newHash);
                    clientDetailsToBeUpdated.add(client);

                    assets.setSyncState(newHash);
                    assetsToBeUpdated.add(assets);
                }
            });

            elasticBulkIndexer.bulkIndexClients(profilesToBeUpdated);
            clientDetailsRepo.saveAll(clientDetailsToBeUpdated);

            clientsAssetsLiabilitiesRepo.saveAll(assetsToBeUpdated);
            clientsAssetsLiabilitiesRepo.flush(); // forces the update to execute immediately
                
            log.info("Synced {} client profiles, {} client details, and {} assets/liabilities.", profilesToBeUpdated.size(), clientDetailsToBeUpdated.size(), assetsToBeUpdated.size());
    }
}