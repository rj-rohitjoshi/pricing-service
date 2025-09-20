package com.ecommerce.pricing.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pricing_rules")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PricingRule {
    @Id
    private String id;
    private String sku;
    private Long productId;
    private BigDecimal basePrice;
    private BigDecimal currentPrice;
    private String currency;
    private List<Discount> discounts;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Discount {
        private String type; // PERCENTAGE, FIXED_AMOUNT
        private BigDecimal value;
        private String description;
        private LocalDateTime validFrom;
        private LocalDateTime validTo;
    }
}
