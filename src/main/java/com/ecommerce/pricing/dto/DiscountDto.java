package com.ecommerce.pricing.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DiscountDto {
    private String type;
    private BigDecimal value;
    private String description;
    private LocalDateTime validFrom;
    private LocalDateTime validTo;
}
