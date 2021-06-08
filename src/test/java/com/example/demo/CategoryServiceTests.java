package com.example.demo;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryRepository;
import com.example.demo.category.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests{
    @Mock
    private CategoryRepository categoryRepository;
    @Captor
    ArgumentCaptor<Category> captor;
    @Test
    void getCategories() {
        Category category1 = new Category();
        category1.setName("Аппаратура");
        Category category2 = new Category();
        category2.setName("Техника");
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(category1,
                category2));
        assertEquals(2,
                categoryRepository.findAll().size());
        assertEquals("Аппаратура",
                categoryRepository.findAll().get(0).getName());
    }
    @Test
    void deleteCategories() {
        Category bank1 = new Category();
        bank1.setName("Аппаратура");
        CategoryService us =new CategoryService(categoryRepository);
        us.create(bank1);
        Mockito.verify(categoryRepository).save(captor.capture());
        Category captured = captor.getValue();
        assertEquals("Аппаратура", captured.getName());
        us.delete("Аппаратура");
    }
    @Test
    void createCategories() {
        Category bank1 = new Category();
        bank1.setName("Аппаратура");
        CategoryService us =new CategoryService(categoryRepository);
        us.create(bank1);
        Mockito.verify(categoryRepository).save(captor.capture());
        Category captured = captor.getValue();
        assertEquals("Аппаратура", captured.getName());
    }
}