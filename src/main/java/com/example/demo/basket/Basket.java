package com.example.demo.basket;

import com.example.demo.product.Product;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Data
@Table(name = "basket")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "volume", length = 200, nullable = false)
    int volume;
    @JoinColumn(name="product_id")
    @ManyToOne
    private Product product;
    @JoinColumn(name="users_id")
    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Busket{" +
                "id=" + id +
                ", volume='" + volume + '\'' +
                ", user_id='" + user + '\'' +
                ", product_id=" + product + '\'' +
                '}';
    }

}
