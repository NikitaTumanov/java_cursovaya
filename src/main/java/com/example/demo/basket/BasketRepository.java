package com.example.demo.basket;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс реализующий работу с репозиторием корзины.
 * А именно: поиск корзины по id, поиск всех корзин по пользователю, удалить корзину по юзеру, удалить корзину по id.
 */
@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findById(long id);
    Basket findAllByUser(User user);
    Long deleteByUser(User user);
    Long deleteById(long id);
}
