package com.ecommerce.pricing.service;

import com.ecommerce.pricing.dto.PricingDto;
import java.math.BigDecimal;
import java.util.List;

public interface PricingService {
    PricingDto createOrUpdate(PricingDto dto);
    PricingDto getBySku(String sku);
    List<PricingDto> getByProductId(Long productId);
    List<PricingDto> findAll();
    BigDecimal calculateFinalPrice(String sku);
    void deleteBySku(String sku);
}
