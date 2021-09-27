package com.example.demo;

import com.example.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Класс, отвечающий за безопасность и авторизацию.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * Сервис работы с пользователями.
     */
    private UserService userService;

    /**
     * Конструктор.
     * @param userService Объект сервиса работы с пользователями.
     */
    @Autowired
    public void setUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод для аутентификации пользователя.
     * @param auth Объект используется для построения аутентификации AuthenticationManager.
     * @throws Exception Класс, отвечающийза обнаружение ошибок.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * Метод настройки безопасности и информации о пользователях.
     * @param http Объект для настройки веб-безопасности для определенных HTTP-запросов.
     * @throws Exception Класс, отвечающийза обнаружение ошибок.
     */
    @Override
    protected void configure(HttpSecurity http) throws
            Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/login", "/logout", "/css/**", "/sign", "/signUperror").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

}