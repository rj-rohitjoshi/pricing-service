package com.ecommerce.pricing.service;

import com.ecommerce.pricing.document.PricingRule;
import com.ecommerce.pricing.dto.DiscountDto;
import com.ecommerce.pricing.dto.PricingDto;
import com.ecommerce.pricing.exception.PricingRuleNotFoundException;
import com.ecommerce.pricing.repository.PricingRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PricingServiceImpl implements PricingService {
    private final PricingRuleRepository repository;

    @Override
    public PricingDto createOrUpdate(PricingDto dto) {
        PricingRule rule = convertToDocument(dto);
        rule.setCreatedAt(LocalDateTime.now());
        rule.setUpdatedAt(LocalDateTime.now());

        PricingRule saved = repository.save(rule);
        return convertToDto(saved);
    }

    @Override
    public PricingDto getBySku(String sku) {
        PricingRule rule = repository.findBySku(sku)
                .orElseThrow(() -> new PricingRuleNotFoundException("Pricing rule not found for SKU: " + sku));
        return convertToDto(rule);
    }

    @Override
    public List<PricingDto> getByProductId(Long productId) {
        return repository.findByProductId(productId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PricingDto> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateFinalPrice(String sku) {
        PricingRule rule = repository.findBySku(sku)
                .orElseThrow(() -> new PricingRuleNotFoundException("Pricing rule not found for SKU: " + sku));

        BigDecimal finalPrice = rule.getBasePrice();

        if (rule.getDiscounts() != null) {
            for (PricingRule.Discount discount : rule.getDiscounts()) {
                if (isDiscountValid(discount)) {
                    finalPrice = applyDiscount(finalPrice, discount);
                }
            }
        }

        return finalPrice;
    }

    @Override
    public void deleteBySku(String sku) {
        PricingRule rule = repository.findBySku(sku)
                .orElseThrow(() -> new PricingRuleNotFoundException("Pricing rule not found for SKU: " + sku));
        repository.delete(rule);
    }

    private boolean isDiscountValid(PricingRule.Discount discount) {
        LocalDateTime now = LocalDateTime.now();
        return (discount.getValidFrom() == null || now.isAfter(discount.getValidFrom())) &&
                (discount.getValidTo() == null || now.isBefore(discount.getValidTo()));
    }

    private BigDecimal applyDiscount(BigDecimal price, PricingRule.Discount discount) {
        if ("PERCENTAGE".equals(discount.getType())) {
            BigDecimal discountAmount = price.multiply(discount.getValue().divide(BigDecimal.valueOf(100)));
            return price.subtract(discountAmount);
        } else if ("FIXED_AMOUNT".equals(discount.getType())) {
            return price.subtract(discount.getValue());
        }
        return price;
    }

    private PricingDto convertToDto(PricingRule rule) {
        List<DiscountDto> discountDtos = rule.getDiscounts() != null ?
                rule.getDiscounts().stream()
                        .map(d -> DiscountDto.builder()
                                .type(d.getType())
                                .value(d.getValue())
                                .description(d.getDescription())
                                .validFrom(d.getValidFrom())
                                .validTo(d.getValidTo())
                                .build())
                        .collect(Collectors.toList()) : null;

        return PricingDto.builder()
                .id(rule.getId())
                .sku(rule.getSku())
                .productId(rule.getProductId())
                .basePrice(rule.getBasePrice())
                .currentPrice(rule.getCurrentPrice())
                .currency(rule.getCurrency())
                .discounts(discountDtos)
                .validFrom(rule.getValidFrom())
                .validTo(rule.getValidTo())
                .build();
    }

    private PricingRule convertToDocument(PricingDto dto) {
        List<PricingRule.Discount> discounts = dto.getDiscounts() != null ?
                dto.getDiscounts().stream()
                        .map(d -> PricingRule.Discount.builder()
                                .type(d.getType())
                                .value(d.getValue())
                                .description(d.getDescription())
                                .validFrom(d.getValidFrom())
                                .validTo(d.getValidTo())
                                .build())
                        .collect(Collectors.toList()) : null;

        return PricingRule.builder()
                .id(dto.getId())
                .sku(dto.getSku())
                .productId(dto.getProductId())
                .basePrice(dto.getBasePrice())
                .currentPrice(dto.getCurrentPrice())
                .currency(dto.getCurrency())
                .discounts(discounts)
                .validFrom(dto.getValidFrom())
                .validTo(dto.getValidTo())
                .build();
    }
}
