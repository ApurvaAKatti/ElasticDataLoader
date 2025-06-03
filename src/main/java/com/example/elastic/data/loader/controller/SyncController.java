package com.example.elastic.data.loader.controller;

import com.example.elastic.data.loader.services.SyncService;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sync Operations", description = "Endpoints for syncing client data")
@RestController
@RequestMapping("/sync")
public class SyncController {

    @Autowired
    private SyncService syncService;

    @Operation(summary = "Sync client data", description = "Syncs client details and assets/liabilities for the given clientId")
    @GetMapping("/{clientId}")
    public ResponseEntity<String> syncClient(@PathVariable Integer clientId) {
        try {
            syncService.syncClient(clientId);
            return ResponseEntity.ok("Client data synced successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error syncing client data: " + e.getMessage());
        }
    }

    @Operation(summary = "Sync all client data", description = "Syncs client details and assets/liabilities for all available data in MySql and Oracle databases")
    @GetMapping("/all")
    public ResponseEntity<String> syncAllClients() {
        try {
            syncService.syncAllClients();
            return ResponseEntity.ok("Client data synced successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error syncing client data: " + e.getMessage());
        }
    }
}