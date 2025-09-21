package com.ecommerce.pricing.service;

import com.ecommerce.pricing.dto.PricingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class PricingServiceTest {

    @Autowired
    private PricingService service;

    @Test
    void createAndFindPricingRule() {
        PricingDto dto = PricingDto.builder()
                .sku("ITEM-999")
                .productId(999L)
                .basePrice(BigDecimal.valueOf(100.00))
                .currentPrice(BigDecimal.valueOf(100.00))
                .currency("USD")
                .build();

        PricingDto created = service.createOrUpdate(dto);
        assertThat(created.getId()).isNotNull();

        PricingDto found = service.getBySku("ITEM-999");
        assertThat(found.getBasePrice()).isEqualTo(BigDecimal.valueOf(100.00));
    }
}
