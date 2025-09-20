# Pricing Service

Standalone Spring Boot microservice for dynamic pricing and discount management, backed by MongoDB.

## Build and Run

mvn clean package
mvn spring-boot:run

or
docker build -t pricing-service .
docker run -p 8083:8083 pricing-service


## Features

- Dynamic pricing rules
- Percentage and fixed-amount discounts
- Time-based discount validation
- MongoDB document storage

## REST API

- `POST /pricing` - create/update pricing rule
- `GET /pricing` - list all rules
- `GET /pricing/{sku}` - get rule by SKU
- `GET /pricing/product/{productId}` - rules by product ID
- `GET /pricing/{sku}/final-price` - calculate final price with discounts
- `DELETE /pricing/{sku}` - delete rule

## Test

Uses Testcontainers with MongoDB.
