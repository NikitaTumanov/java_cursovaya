package com.example.demo.product;

import com.example.demo.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @Column(name = "productName", length = 200, nullable = false)
    String productName;
    @Column(name = "productVolume", length = 200, nullable = false)
    int productVolume;
    @Column(name = "productPrice", length = 200, nullable = false)
    int productPrice;
    @ManyToOne
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productVolume='" + productVolume + '\'' +
                ", productPrice=" + productPrice + '\'' +
                '}';
    }
}

