package com.example.demo;

import com.example.demo.basket.Basket;
import com.example.demo.basket.BasketRepository;
import com.example.demo.basket.BasketService;
import com.example.demo.category.Category;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.product.ProductService;
import com.example.demo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CriteriaServiceTests {
    @InjectMocks
    private BasketService basketService;
    @InjectMocks
    private ProductService productService;
    @Mock
    private CriteriaService criteriaService;
    @Mock
    private BasketRepository basketRepository;
    @Mock
    private ProductRepository productRepository;
    @Captor
    private ArgumentCaptor<Basket> basketArgumentCaptor;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    private Product product1;
    private User user;
    private Category category;
    private Basket basket1;
    @BeforeEach
    void init() {
        category = new Category();
        category.setId(1);
        category.setName("category1");

        product1 = new Product();
        product1.setId(1);
        product1.setProductPrice(1000);
        product1.setProductName("product1");
        product1.setProductVolume(99);
        product1.setCategory(category);

        user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setName("user1");
        user.setPassword("password");
        user.setType("role");

        basket1 = new Basket();
        basket1.setId(1L);
        basket1.setProduct(product1);
        basket1.setVolume(100);
        basket1.setUser(user);
    }
    @Test
    void takeProductByCategory() {
        productService.create(product1);
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());
        Mockito.when(criteriaService.takeByCategory(product1.getCategory().getName())).thenReturn(List.of(product1));
        assertEquals(1, criteriaService.takeByCategory(product1.getCategory().getName()).size());

        Mockito.when(criteriaService.takeByCategory("Wrong category")).thenReturn(List.of());
        assertEquals(0, criteriaService.takeByCategory("Wrong category").size());
    }

    @Test
    void takeBasketByUser() {
        basketService.create(basket1);
        Mockito.verify(basketRepository).save(basketArgumentCaptor.capture());
        Mockito.when(criteriaService.takeByUser(user.getName())).thenReturn(List.of(basket1));
        assertEquals(1, criteriaService.takeByUser(user.getName()).size());

        Mockito.when(criteriaService.takeByUser("Wrong user")).thenReturn(List.of());
        assertEquals(0, criteriaService.takeByUser("Wrong user").size());
    }

    @Test
    void takeProductByProductName() {
        productService.create(product1);
        Mockito.verify(productRepository).save(productArgumentCaptor.capture());
        Mockito.when(criteriaService.takeByProductName(product1.getProductName())).thenReturn(List.of(product1));
        assertEquals(1, criteriaService.takeByProductName(product1.getProductName()).size());

        Mockito.when(criteriaService.takeByProductName("Wrong name")).thenReturn(List.of());
        assertEquals(0, criteriaService.takeByProductName("Wrong name").size());
    }

}