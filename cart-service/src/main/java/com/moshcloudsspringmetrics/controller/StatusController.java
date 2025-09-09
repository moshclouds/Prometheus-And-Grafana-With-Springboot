package com.moshcloudsspringmetrics.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class StatusController {
    @GetMapping("/")
    public ResponseEntity<Map<String,Object>> getStatus() {
        Map<String,Object> response = new HashMap<>();
        response.put("timestamp",LocalDateTime.now().toString());
        response.put("status",LocalDateTime.now().toString());
        response.put("message",LocalDateTime.now().toString());

        Map<String,Object> data = new HashMap<>();
        data.put("service", "cart-service");
        data.put("health", "UP");

        response.put("data", data);
        
        return ResponseEntity.ok(response);
    }
    
}
