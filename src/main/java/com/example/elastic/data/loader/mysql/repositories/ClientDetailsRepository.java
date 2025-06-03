package com.example.elastic.data.loader.mysql.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.elastic.data.loader.mysql.entities.ClientDetails;

@Repository("clientDetailsRepository")
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Integer> {
    // You can add custom query methods if needed, for example:
    ClientDetails findByClientId(Integer clientId);
    List<ClientDetails> findAll();
}
