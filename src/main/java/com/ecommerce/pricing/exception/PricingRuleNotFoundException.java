package com.ecommerce.pricing.exception;

public class PricingRuleNotFoundException extends RuntimeException {
    public PricingRuleNotFoundException(String message) {
        super(message);
    }
}
