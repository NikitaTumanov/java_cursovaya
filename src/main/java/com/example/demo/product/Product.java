package com.example.demo.product;

import com.example.demo.basket.Basket;
import com.example.demo.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
/**
 * Класс формирует объект product.
 */
@Getter
@Setter

@Entity
@Data
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    /**
     * Присваиваем уникальный id объекту.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Название товара productName связывем с колонкой name в таблице product.
     */
    @Column(name = "name", length = 200, nullable = false)
    String productName;
    /**
     * Количество товаров productVolume связывем с колонкой volume в таблице product.
     */
    @Column(name = "volume", length = 200, nullable = false)
    int productVolume;
    /**
     * Цена товара productPrice связывем с колонкой price в таблице product.
     */
    @Column(name = "price", length = 200, nullable = false)
    int productPrice;
    /**
     * Привязываем товар к категории со связью "многие к одному".
     */
    @JsonIgnore
    @ManyToOne
    private Category category;
    /**
     * Формируем список корзин связанных с данным продуктом со связью "один ко многим".
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Basket> baskets;

    /**
     * Метод преобразовывает поле productName в строку.
     * @return Возвращение строки с именем в товара.
     */
    @Override
    public String toString() {
        return productName;
    }
}

