package com.example.app.storage.dao.impl;

import com.example.app.storage.dao.OrderDao;
import com.example.app.storage.model.Order;
import com.example.app.storage.util.HibernateSessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public Long save(Order obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(obj);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Order get(Long id) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        return session.get(Order.class, id);
    }

    @Override
    public List<Order> list() {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        List<Order> objects = session.createQuery("from Order", Order.class).getResultList();
        session.close();
        return objects;
    }

    @Override
    public void update(Order obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Order obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }
}
