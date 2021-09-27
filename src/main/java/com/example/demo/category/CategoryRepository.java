package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Интерфейс реализующий работу с репозиторием категории.
 * А именно: удаление корзины по названию, поиск категории по названию.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Long deleteByName(String name);
    Category findByName(String name);
}