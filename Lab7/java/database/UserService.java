package database;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserService {

    private UserDao usersDao = new UserDao();

    public UserService() {
    }

    public User findUser(String login) {
        return usersDao.findByLogin(login);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        List<User> list = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            session.beginTransaction();

            Query query = session.createQuery("from User");
            list = (List<User>) query.list();

            session.getTransaction().commit();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        if (list != null && !list.isEmpty()) {
            for (User user : list) {
                    System.out.println(user);
                }
            }
        return list;
    }

    public SpaceMarines findSpaceMarineById(int id) {
        return usersDao.findSpaceMarineById(id);
    }
}
