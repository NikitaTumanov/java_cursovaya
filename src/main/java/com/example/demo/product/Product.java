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

@Getter
@Setter

@Entity
@Data
@Table(name = "product")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 200, nullable = false)
    String productName;
    @Column(name = "volume", length = 200, nullable = false)
    int productVolume;
    @Column(name = "price", length = 200, nullable = false)
    int productPrice;
    @JsonIgnore
    @ManyToOne
    private Category category;


    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Basket> baskets;

    @Override
    public String toString() {
        return productName;
    }
}

