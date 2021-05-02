package com.example.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Long deleteByProductName(String productName);

    Product findByProductName(String productName);
}