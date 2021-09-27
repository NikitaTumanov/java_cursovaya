package com.example.demo.category;

import com.example.demo.product.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
/**
 * Класс формирует объект category.
 */
@Getter
@Setter

@ToString
@Entity
@Data
@Table(name = "category")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category {
    /**
     * Присваиваем уникальный id объекту.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Название категории name связывем с колонкой name в таблице category.
     */
    @NaturalId
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    /**
     * Формируем список продуктов связанный с данной категорией со связью "один ко многим".
     */
    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;
}
