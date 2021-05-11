package com.dataart.cerebro.controller;

import com.dataart.cerebro.domain.Type;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {
    @GetMapping("/api/types")
    public ResponseEntity<?> getTypes() {
        return ResponseEntity.ok(Type.values());
    }
}
