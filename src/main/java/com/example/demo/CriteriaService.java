package com.example.demo;

import com.example.demo.category.Category;
import com.example.demo.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CriteriaService {
    private final SessionFactory sessionFactory;
    private Session session;

    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    @PreDestroy
    void closeSession() {
        session.close();
    }

    public List<Category> takeByName() {
        log.info("Find Banks, whose Name includes SDM");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Category> orderCriteriaQuery = builder.createQuery(Category.class);
        Root<Category> root = orderCriteriaQuery.from(Category.class);
        orderCriteriaQuery.select(root).where(builder.like(root.get("name"), "%SDM%"));
        Query<Category> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }

    public List<Product> takeByProductName() {
        log.info("Find Cards, whose CardNumber is 123");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("cardNumber"), 123));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }

    public List<Product> takeByCategory_id() {
        log.info("Find Cards, whose Code is 123");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("code"), 123));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }
}