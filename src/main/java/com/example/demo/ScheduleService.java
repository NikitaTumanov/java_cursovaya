package com.example.demo;

import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Класс обновления значений количества товаров раз в месяц.
 * Периодичность обновления указывается в @Scheduled(cron = "0 0 13 1 * *").
 */
@Component
public class ScheduleService {
    /**
     * Сервис работы с фильтрацией.
     */
    @Autowired
    private CriteriaService cus;
    /**
     * Сервис работы с продуктами.
     */
    @Autowired
    private ProductService productService;

    /**
     * Периодичность обновления указывается в @Scheduled(cron = "0 0 13 1 * *").
     * Метод создает список продуктов и наполняет его товарами конкретной категории, после чего изменяет при помощи
     * потоков в БД их количество.
     */
    @SneakyThrows
    @Scheduled(cron = "0 0 13 1 * *")
    @ManagedOperation
    public void update_volume() {
        List<Product> product = cus.takeByCategory("Строительные материалы");
        product.forEach(product1 -> {product1.setProductVolume(1000); productService.create(product1);});

        product = cus.takeByCategory("Инструменты");
        product.forEach(product2 -> {product2.setProductVolume(300); productService.create(product2);});

        product = cus.takeByCategory("Техника");
        product.forEach(product3 -> {product3.setProductVolume(50); productService.create(product3);});

        product = cus.takeByCategory("Аппаратура");
        product.forEach(product4 -> {product4.setProductVolume(90); productService.create(product4);});

    }
}