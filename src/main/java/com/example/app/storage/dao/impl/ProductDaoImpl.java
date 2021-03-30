package com.example.app.storage.dao.impl;

import com.example.app.storage.dao.ProductDao;
import com.example.app.storage.model.OrderEntry;
import com.example.app.storage.model.Product;
import com.example.app.storage.util.HibernateSessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ProductDaoImpl implements ProductDao {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public Long save(Product obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(obj);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Product get(Long id) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        return session.get(Product.class, id);
    }

    @Override
    public List<Product> list() {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        List<Product> objects = session.createQuery("from Product", Product.class).getResultList();
        session.close();
        return objects;
    }

    @Override
    public void update(Product obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Product obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }

}
