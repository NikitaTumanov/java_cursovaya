package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Основной класс Java Spring.
 */
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

    /**
     * Метод запуска SpringApplication.
     * @param args Аргументы командной строки, с которыми запускается приложение.
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
