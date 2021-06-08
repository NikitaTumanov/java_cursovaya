package com.example.demo;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.ProductService;
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
public class ProductServiceTests {
    @Mock
    private ProductRepository productRepository;
    @Captor
    ArgumentCaptor<Product> captor;
    @Test
    void getProducts() {
        Product product1 = new Product();
        product1.setProductName("Лопата");
        Product product2 = new  Product();
        product2.setProductPrice(123);
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1,product2));
        assertEquals(2,
                productRepository.findAll().size());

    }

    @Test
    void deleteProducts() {
        Product product1 = new Product();
        product1.setProductName("Лопата");
        Product product2 = new  Product();
        product2.setProductPrice(123);
        Mockito.when(productRepository.findAll()).thenReturn(List.of(product1,product2));
        assertEquals(2,
                productRepository.findAll().size());

        ProductService cs = new ProductService(productRepository);
        cs.delete("Лопата");
    }

    @Test
    void createProducts() {
        Product product1 = new Product();
        product1.setProductPrice(123);
        ProductService cs = new ProductService(productRepository);
        cs.create(product1);
        Mockito.verify(productRepository).save(captor.capture());
        Product captured = captor.getValue();
        assertEquals(123, captured.getProductPrice());
    }

}