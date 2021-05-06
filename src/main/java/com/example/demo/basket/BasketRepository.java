package com.example.demo.basket;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findById(long id);
    Basket findAllByUser(User user);
    Long deleteByUser(User user);
    Long deleteById(long id);
}
