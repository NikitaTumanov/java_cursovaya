package com.example.demo.user;

import com.example.demo.basket.Basket;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
/**
 * Класс формирует объект user.
 */
@Getter
@Setter
@ToString
@Data
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    /**
     * Присваиваем уникальный id объекту.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Имя пользователя name связывем с колонкой name в таблице users.
     */
    @NaturalId
    @Column(name = "name", length = 200, nullable = false)
    private String name;
    /**
     * Пароль пользователя password связывем с колонкой password в таблице users.
     */
    @Column(name = "password", length = 200, nullable = false)
    private String password;
    /**
     * Почта пользователя email связывем с колонкой email в таблице users.
     */
    @Column(name = "email", length = 200, nullable = false)
    private String email;
    /**
     * Тип пользователя type связывем с колонкой type в таблице users.
     */
    @Column(name = "type", length = 200, nullable = false)
    private String type;
    /**
     * Привязываем пользователя к корзине со связью "один ко многим".
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Basket> baskets;

    /**
     * Метод работы с коллекциями.
     * @return Вернуть список с новым авторизированным пользователем.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }

    /**
     * Метод получения имени пользователя.
     * @return Вернуть имя пользователя.
     */
    @Override
    public String getUsername() {
        return getName();
    }

    /**
     * Метод проверки активности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Метод проверки незаблокированности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Метод проверки учетных данных аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Метод проверки доступности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Метод преобразовывает объект user d строку.
     * @return Возвращение строки с информацией о пользователе.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type + '\'' +
                '}';
    }
}