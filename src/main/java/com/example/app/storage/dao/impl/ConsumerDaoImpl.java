package com.example.app.storage.dao.impl;

import com.example.app.storage.dao.ConsumerDao;
import com.example.app.storage.model.Consumer;
import com.example.app.storage.util.HibernateSessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ConsumerDaoImpl implements ConsumerDao {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    @Override
    public Long save(Consumer obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Long id = (Long) session.save(obj);
        transaction.commit();
        session.close();
        return id;
    }

    @Override
    public Consumer get(Long id) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        return session.get(Consumer.class, id);
    }

    @Override
    public List<Consumer> list() {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        List<Consumer> consumerList = session.createQuery("from Consumer", Consumer.class).getResultList();
        session.close();
        return consumerList;
    }

    @Override
    public void update(Consumer obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Consumer obj) {
        Session session = HibernateSessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(obj);
        transaction.commit();
        session.close();
    }
}
