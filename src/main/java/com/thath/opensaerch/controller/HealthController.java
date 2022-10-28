package com.thath.opensaerch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Health Endpoints
 * */
@RestController
@RequestMapping("/healthCheck")
public class HealthController {

    @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getHealthStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "service is up and running"));
    }

}
