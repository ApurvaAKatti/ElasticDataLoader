package com.example.elastic.data.loader.services;

import org.springframework.stereotype.Service;

@Service
public interface SyncService {
    void syncClient(Integer clientId);
}