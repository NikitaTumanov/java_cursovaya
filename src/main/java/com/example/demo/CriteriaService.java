package com.example.demo;

import com.example.demo.category.Category;
import com.example.demo.category.CategoryService;
import com.example.demo.product.Product;
import com.example.demo.product.ProductService;
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

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CriteriaService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

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

    public List<Product> takeByCategory(String category) {
        log.info("Find products, who has this category");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("category"), categoryService.findByName(category).getId()));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }

    public List<Product> takeByProductName(String name) {
        log.info("Find products, who has this name");
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        orderCriteriaQuery.select(root).where(builder.equal(root.get("productName"), name));
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