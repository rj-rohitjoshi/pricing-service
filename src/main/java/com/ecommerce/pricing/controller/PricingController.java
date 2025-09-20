package com.ecommerce.pricing.controller;

import com.ecommerce.pricing.dto.PricingDto;
import com.ecommerce.pricing.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController @RequestMapping("/pricing")
@RequiredArgsConstructor
public class PricingController {
    private final PricingService service;

    @PostMapping
    public ResponseEntity<PricingDto> createOrUpdate(@RequestBody PricingDto dto) {
        return ResponseEntity.ok(service.createOrUpdate(dto));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<PricingDto> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(service.getBySku(sku));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PricingDto>> getByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getByProductId(productId));
    }

    @GetMapping
    public ResponseEntity<List<PricingDto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{sku}/final-price")
    public ResponseEntity<BigDecimal> getFinalPrice(@PathVariable String sku) {
        return ResponseEntity.ok(service.calculateFinalPrice(sku));
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Void> deleteBySku(@PathVariable String sku) {
        service.deleteBySku(sku);
        return ResponseEntity.noContent().build();
    }
}
