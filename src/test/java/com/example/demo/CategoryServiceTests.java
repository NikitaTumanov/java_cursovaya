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

    private Category productType1, productType2, productType3;
    @BeforeEach
    void init() {
        productType1 = new Category();
        productType1.setId(1);
        productType1.setName("productType1");

        productType2 = new Category();
        productType2.setId(2);
        productType2.setName("productType2");

        productType3 = new Category();
        productType3.setId(3);
        productType3.setName("productType3");
    }
    @Test
    void getProductTypesByPetId() {
        Mockito.when(categoryRepository.findAllByPetId(1)).thenReturn(List.of(productType1, productType2));
        assertEquals(List.of(productType1, productType2), categoryRepository.findAllByPetId(1));
    }

    @Test
    void getAllProductTypes() {
        Mockito.when(categoryRepository.findAll()).thenReturn(List.of(productType1, productType2, productType3));
        assertEquals(3, categoryRepository.findAll().size());
    }

    @Test
    void addProductType() {
        categoryService.addProductType(productType1);
        Mockito.verify(categoryRepository).save(captor.capture());
        Category captured = captor.getValue();
        assertEquals("productType1", captured.getName());
    }

    @Test
    void deleteProductType() {
        categoryService.deleteProductType(1);
        Mockito.verify(categoryRepository).deleteById(1);
    }

    @Test
    void getProductTypeById() {
        Mockito.when(categoryRepository.findById(1)).thenReturn(productType1);
        assertEquals(productType1, categoryRepository.findById(1));
    }
}