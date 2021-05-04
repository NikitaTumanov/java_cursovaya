package com.example.demo;

import com.example.demo.category.CategoryService;
import com.example.demo.product.ProductService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

@Component
public class ScheduleService {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @SneakyThrows
    @Scheduled(cron = "0 0/1 * * * *")
    @ManagedOperation
    public void update_volume() {
        
    }
}