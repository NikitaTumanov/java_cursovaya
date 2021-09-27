package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Интерфейс реализующий работу с репозиторием пользователя.
 * А именно: удаление пользователя по имени, поиск пользователя по имени.
 */
public interface UserRepository extends JpaRepository<User, String> {
    Long deleteByName(String name);
    User findByName(String name);
}