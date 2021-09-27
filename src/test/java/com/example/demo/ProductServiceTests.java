package com.example.demo;

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
        product1.setProductName("product1");
        product1.setProductVolume(99);

        product2 = new Product();
        product2.setId(2);
        product2.setProductPrice(120);
        product2.setProductName("product2");
        product2.setProductVolume(3);

        product3 = new Product();
        product3.setId(3);
        product3.setProductPrice(8200);
        product3.setProductName("product3");
        product3.setProductVolume(29);
    }
    @Test
    void getProductsById() {
        Mockito.when(productRepository.findAllById(0)).thenReturn(List.of());
        Mockito.when(productRepository.findByProductName("product3")).thenReturn(product3);
        assertEquals(List.of(), productRepository.findAllById(0));
        assertEquals(product3, productRepository.findByProductName("product3"));
    }
    @Test
    void getAllProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));
        assertEquals(List.of(product1, product2, product3),productRepository.findAll());
    }
    @Test
    void deleteProduct() {
        productService.delete("product2");
        Mockito.verify(productRepository).deleteByProductName("product2");
    }
    @Test
    void addProduct() {
        productService.create(product3);
        Mockito.verify(productRepository).save(captor.capture());
        Product captured = captor.getValue();
        assertEquals("product3", captured.getProductName());
    }
}