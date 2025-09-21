package com.ecommerce.pricing.repository;

import com.ecommerce.pricing.document.PricingRule;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface PricingRuleRepository extends MongoRepository<PricingRule, String> {
    Optional<PricingRule> findBySku(String sku);
    List<PricingRule> findByProductId(Long productId);
    List<PricingRule> findByCurrency(String currency);
}
