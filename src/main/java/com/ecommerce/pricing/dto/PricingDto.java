package com.ecommerce.pricing.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PricingDto {
    private String id;
    private String sku;
    private Long productId;
    private BigDecimal basePrice;
    private BigDecimal currentPrice;
    private String currency;
    private List<DiscountDto> discounts;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
