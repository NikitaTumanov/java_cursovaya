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
    private String banksFile = "src/main/resources/Data/banks.txt";
    private String cardsFile = "src/main/resources/Data/cards.txt";
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @SneakyThrows
    @Scheduled(cron = "0 0/1 * * * *")
    @ManagedOperation
    public void delete_write_from_db() {
        File f1 = new File(banksFile);
        f1.delete();
        File f2 = new File(cardsFile);
        f2.delete();
        FileOutputStream fileUniversity = new FileOutputStream(banksFile);
        FileOutputStream fileStudents = new FileOutputStream(cardsFile);
        fileUniversity.write(categoryService.readAll().toString().getBytes(StandardCharsets.UTF_8));
        fileStudents.write(productService.readAll().toString().getBytes(StandardCharsets.UTF_8));
    }
}