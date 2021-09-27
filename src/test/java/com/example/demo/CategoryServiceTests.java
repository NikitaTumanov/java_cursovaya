package com.example.demo;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryRepository;
import com.example.demo.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests{
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Captor
    private ArgumentCaptor<Category> captor;

    private Category category1, category2, category3;
    @BeforeEach
    void init() {
        category1 = new Category();
        category1.setId(1);
        category1.setName("category1");

        category2 = new Category();
        category2.setId(2);
        category2.setName("category2");

        category3 = new Category();
        category3.setId(3);
        category3.setName("category3");
    }
    @Test
    void getCategoryById() {
        Mockito.when(categoryRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(category1));
        assertEquals(java.util.Optional.ofNullable(category1), categoryRepository.findById("1"));
    }
    @Test
    void getAllCategory() {
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category1, category2, category3));
        assertEquals(3, categoryRepository.findAll().size());
    }
    @Test
    void addCategory() {
        categoryService.create(category1);
        Mockito.verify(categoryRepository).save(captor.capture());
        Category captured = captor.getValue();
        assertEquals("category1", captured.getName());
    }
    @Test
    void deleteCategory() {
        categoryService.delete("category1");
        Mockito.verify(categoryRepository).deleteByName("category1");
    }
    @Test
    void getCategoryByName() {
        Mockito.when(categoryRepository.findByName("category1")).thenReturn(category1);
        assertEquals(category1, categoryRepository.findByName("category1"));
    }
}