package com.example.app.storage.util;

import com.example.app.storage.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class HibernateSessionFactoryProvider {

    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryProvider() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Consumer.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(OrderEntry.class);
                configuration.addAnnotatedClass(Product.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
