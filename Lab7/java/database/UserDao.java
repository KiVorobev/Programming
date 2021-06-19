package database;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDao {
    public User findByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.createSQLQuery("select from USER where login=" + login)
                .addEntity(User.class)
                .list()
                .forEach(System.out::println);
        User user = session.get(User.class, login);
        return null;
    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public SpaceMarines findSpaceMarineById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(SpaceMarines.class, id);
    }
}
