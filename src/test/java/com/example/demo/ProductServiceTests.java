package com.example.demo;

import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    private ProductService productService;
    private CategoryService categoryService;
    @Mock
    private ProductRepository productRepository;
    @Captor
    private ArgumentCaptor<Product> captor;

    private Product product1, product2, product3;
    @BeforeEach
    void init() {

        product1 = new Product();
        product1.setId(1);
        product1.setProductPrice(1000);
        product1.setProductName("Трактор");
        product1.setProductVolume(99);
        product1.setCategory(categoryService.findByName("Техника"));

        product2 = new Product();
        product2.setId(2);
        product2.setProductPrice(120);
        product2.setProductName("Лазер");
        product2.setProductVolume(3);
        product2.setCategory(categoryService.findByName("Аппаратура"));

        product3 = new Product();
        product3.setId(3);
        product3.setProductPrice(8200);
        product3.setProductName("Лопата");
        product1.setProductVolume(29);
        product1.setCategory(categoryService.findByName("Инструменты"));
    }
    @Test
    void getProductsByIds() {
        Mockito.when(productRepository.findAllByPetId(0)).thenReturn(List.of());
        Mockito.when(productRepository.findAllByPetId(1)).thenReturn(List.of(product1, product2));
        Mockito.when(productRepository.findAllByPetIdAndProductTypeId(1, 1)).thenReturn(List.of(product1, product2));
        Mockito.when(productRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(1, 1, 1)).thenReturn(List.of(product1, product2));
        assertEquals(List.of(), productRepository.findAllByPetId(0));
        assertEquals(List.of(product1, product2), productRepository.findAllByPetId(1));
        assertEquals(List.of(product1, product2), productRepository.findAllByPetIdAndProductTypeId(1, 1));
        assertEquals(List.of(product1, product2), productRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(1, 1, 1));
    }

    @Test
    void getProductById() {
        Mockito.when(productRepository.findById(3)).thenReturn(product3);
        assertEquals("product3",productRepository.findById(3).getName());
    }

    @Test
    void getAllProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));
        assertEquals(List.of(product1, product2, product3),productRepository.findAll());
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1);
        Mockito.verify(productRepository).deleteById(1);
    }

    @Test
    void addProduct() {
        productService.addProduct(product3);
        Mockito.verify(productRepository).save(captor.capture());
        Product captured = captor.getValue();
        assertEquals("product3", captured.getName());
    }
}