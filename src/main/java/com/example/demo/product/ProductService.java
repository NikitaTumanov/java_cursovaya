package com.example.demo.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Класс работы с БД продуктов.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProductService {
    /**
     * Объект, реализующий методы репозитория продуктов.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Конструктор ProductService.
     * @param productRepository Репозиторий продуктов.
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Метод сохранения нового продукта.
     * @param u Объект новосозданного продукта.
     */
    public void create(Product u) {
        log.info("Save product {}", u);
        productRepository.save(u);
    }

    /**
     * Метод получения всех продуктов.
     * @return Вернуть список всех продуктов.
     */
    public List<Product> readAll() {
        log.info("Find all products");
        return productRepository.findAll();
    }

    /**
     * Метод поиска продуктов по названию.
     * @param productName Название продукта.
     * @return Вернуть искомый продукт.
     */
    public Product findByProductName(String productName) {
        log.info("Find product, whose productName = {}", productName);
        return productRepository.findByProductName(productName);
    }

    /**
     * Метод удаления продукта по названию.
     * @param productName Название продукта.
     */
    public void delete(String productName) {
        log.info("Delete  product, whose productName = {}", productName);
        productRepository.deleteByProductName(productName);
    }
}