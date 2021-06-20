package database;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SpaceMarinesDao {
    public List<SpaceMarines> findAll() {
        List<SpaceMarines> list = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from SpaceMarines");
            list = (List<SpaceMarines>) query.list();

            session.getTransaction().commit();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return list;
    }

    public void save(SpaceMarines spaceMarines) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(spaceMarines);
        tx1.commit();
        session.close();
    }

    public void delete(SpaceMarines spaceMarines) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(spaceMarines);
        tx1.commit();
        session.close();
    }
}
