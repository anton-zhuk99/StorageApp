package com.example.app.storage.dao.impl;

import com.example.app.storage.dao.OrderEntryDao;
import com.example.app.storage.model.Order;
import com.example.app.storage.model.OrderEntry;
import com.example.app.storage.util.HibernateSessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class OrderEntryDaoImpl implements OrderEntryDao {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public Long save(OrderEntry obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(obj);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public OrderEntry get(Long id) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        return session.get(OrderEntry.class, id);
    }

    @Override
    public List<OrderEntry> list() {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        List<OrderEntry> objects = session.createQuery("from OrderEntry", OrderEntry.class).getResultList();
        session.close();
        return objects;
    }

    @Override
    public void update(OrderEntry obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(OrderEntry obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }

}
