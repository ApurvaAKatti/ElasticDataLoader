package com.example.elastic.data.loader.oracle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.elastic.data.loader.oracle.entities.ClientsAssetsLiabilities;

@Repository("clientsAssetsLiabilitiesRepository")
public interface ClientsAssetsLiabilitiesRepository extends JpaRepository<ClientsAssetsLiabilities, Integer> {
    // You can add custom query methods if needed, for example:
    ClientsAssetsLiabilities findByClientId(Integer clientId);

}