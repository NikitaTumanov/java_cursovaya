package com.example.demo.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void create(Product u) {
        log.info("Save product {}", u);
        productRepository.save(u);
    }

    public List<Product> readAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    public Product findByProductName(String productName) {
        log.info("Find product, whose productName = {}", productName);
        return productRepository.findByProductName(productName);
    }

    public void delete(String productName) {
        log.info("Delete  product, whose productName = {}", productName);
        productRepository.deleteByProductName(productName);
    }
}