package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateSessionFactoryUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        configuration.addAnnotatedClass(User.class).addResource("user.hbm.xml");
        configuration.addAnnotatedClass(SpaceMarines.class).addResource("spacemarine.hbm.xml");
        serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}