package com.example.demo.category;

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
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void create(Category category) {
        log.info("Save category {}", category);
        categoryRepository.save(category);
    }


    public List<Category> readAll() {
        log.info("Find all categories");
        return categoryRepository.findAll();
    }

    public Category findByName(String Name) {
        log.info("Find category, whose Name = {}", Name);
        return categoryRepository.findByName(Name);
    }

    public void delete(String Name) {
        log.info("Delete  category, whose Name = {}", Name);
        categoryRepository.deleteByName(Name);
    }
}