package com.example.demo.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс реализующий работу с репозиторием продукта.
 * А именно: удаление продукта по названию, поиск продукта по названию.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Long deleteByProductName(String productName);

    Product findByProductName(String productName);

    List<Product> findAllById(int i);
}