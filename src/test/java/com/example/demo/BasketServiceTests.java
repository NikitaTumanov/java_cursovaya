package com.example.demo;

import com.example.demo.basket.Basket;
import com.example.demo.basket.BasketRepository;
import com.example.demo.basket.BasketService;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTests {
    @InjectMocks
    private BasketService basketService;
    @Mock
    private BasketRepository basketRepository;
    @Captor
    private ArgumentCaptor<Basket> captor;

    private Basket basket1, basket2, basket3;
    @BeforeEach
    void init() {
        User user1 = new User();
        Product product1 = new Product();
        basket1 = new Basket();
        basket1.setId(1L);
        basket1.setProduct(product1);
        basket1.setVolume(100);
        basket1.setUser(user1);

        basket2 = new Basket();
        basket2.setId(2L);
        basket2.setProduct(product1);
        basket2.setVolume(200);
        basket2.setUser(user1);

        basket3 = new Basket();
        basket3.setId(3L);
        basket3.setProduct(product1);
        basket3.setVolume(300);
        basket3.setUser(user1);
    }
    @Test
    void getBasketById() {
        Mockito.when(basketRepository.findById(1L)).thenReturn(basket1);
        assertEquals(basket1, basketRepository.findById(1L));
    }

    @Test
    void getAllBaskets() {
        Mockito.when(basketRepository.findAll()).thenReturn(List.of(basket1, basket2, basket3));
        assertEquals(3, basketRepository.findAll().size());
    }

    @Test
    void addBasket() {
        basketService.create(basket1);
        Mockito.verify(basketRepository).save(captor.capture());
        Basket captured = captor.getValue();
        assertEquals(1L, captured.getId());
    }

    @Test
    void deleteBasket() {
        basketService.delete(1L);
        Mockito.verify(basketRepository).deleteById(1L);
        basketService.deleteByUser(basket2.getUser());
        Mockito.verify(basketRepository).deleteByUser(basket2.getUser());
    }
}