package com.example.demo.basket;

import com.example.demo.product.Product;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс формирует объект basket.
 */
@Getter
@Setter

@Entity
@Data
@Table(name = "basket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Basket {
    /**
     * Присваиваем уникальный id объекту.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Количество товара volume связывем с колонкой volume в таблице basket.
     */
    @Column(name = "volume", length = 200, nullable = false)
    int volume;
    /**
     * Указываем тип связи "многие ко одному" (несколько корзин к продукту) и записываем в таблицу basket id продукта.
     */
    @JoinColumn(name="product_id")
    @ManyToOne
    private Product product;
    /**
     * Указываем тип связи "многие ко одному" (несколько корзин к пользователю) и записываем в таблицу basket id.
     * пользователя.
     */
    @JoinColumn(name="users_id")
    @ManyToOne
    private User user;

    /**
     * Метод преобразовывает поля product, volume в строку.
     * @return Возвращение строки с товарами в корзине и их количесва.
     */
    @Override
    public String toString() {
        return "Товар:" + product + ", Количество:'" + volume + '\'';
    }

}
