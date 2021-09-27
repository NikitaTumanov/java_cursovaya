package com.example.demo;

import com.example.demo.basket.Basket;
import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Класс, отвечающий за фильтрацию.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CriteriaService {
    /**
     * Сервис работы с категориями.
     */
    @Autowired
    private CategoryService categoryService;
    /**
     * Сервис работы с пользователями.
     */
    @Autowired
    private UserService userService;
    /**
     * Неизменяемый потокобезопасный объект с компилированным маппингом для одной базы данных.
     */
    private final SessionFactory sessionFactory;
    /**
     * Объект используется для операций с базами данных.
     */
    private Session session;

    /**
     * Инициализация сессии для работы с БД. Работает сразу после build проекта.
     */
    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    /**
     * Закрывает сессию перед окончанием работы проекта.
     */
    @PreDestroy
    void closeSession() {
        session.close();
    }

    /**
     * Метод фильтрации продуктов по катеогрии.
     * @param category Название категории.
     * @return Вернуть отфильтрованный список товаров по категории.
     */
    public List<Product> takeByCategory(String category) {
        log.info("Find products, who has this category");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("category"), categoryService.findByName(category).getId()));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }

    /**
     * Метод фильтрации корзин по пользователю.
     * @param name Имя пользователя.
     * @return Вернуть отфильтрованный список корзин.
     */
    public List<Basket> takeByUser(String name) {
        log.info("Find baskets, who has this name");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Basket> orderCriteriaQuery = builder.createQuery(Basket.class);
        Root<Basket> root = orderCriteriaQuery.from(Basket.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("user"), userService.findByName(name)));
        Query<Basket> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }

    /**
     * Метод фильтрации продуктов по названию продукта.
     * @param name Название продукта.
     * @return Вернуть список отфильтрованных продуктов.
     */
    public List<Product> takeByProductName(String name) {
        log.info("Find products, who has this name");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("productName"), name));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }
}