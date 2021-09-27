package com.example.demo.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
 * Класс работы с БД категорий.
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    /**
     * Объект, реализующий методы репозитория категорий.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Конструктор CategoryService.
     * @param categoryRepository Репозиторий категорий.
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Метод сохранения новой категории.
     * @param category Объект новосозданной категории.
     */
    public void create(Category category) {
        log.info("Save category {}", category);
        categoryRepository.save(category);
    }

    /**
     * Метод получения всех категорий.
     * @return Вернуть список всех категорий.
     */
    public List<Category> readAll() {
        log.info("Find all categories");
        return categoryRepository.findAll();
    }

    /**
     * Метод получения категории по названию.
     * @param Name Название категории.
     * @return Вернуть искомую категорию.
     */
    public Category findByName(String Name) {
        log.info("Find category, whose Name = {}", Name);
        return categoryRepository.findByName(Name);
    }

    /**
     * Метод удаления категории по названию.
     * @param Name Название категории.
     */
    public void delete(String Name) {
        log.info("Delete  category, whose Name = {}", Name);
        categoryRepository.deleteByName(Name);
    }
}