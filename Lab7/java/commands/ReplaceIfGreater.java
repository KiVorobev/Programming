package commands;

import data.*;
import database.HibernateSessionFactoryUtil;
import database.SpaceMarines;
import database.SpaceMarinesDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class of command 'replace_if_greater'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class ReplaceIfGreater extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @return - String description of command
     */
    public String action(String in, TreeMap<Integer,SpaceMarine> collection, String login) {
        String message = null;
        try {
            String[] newElement = in.trim().split("\n", 2);
            int key = Integer.parseInt(newElement[0]);
            int health = Integer.parseInt(newElement[1]);

            SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
            List<SpaceMarines> list = null;
            boolean exists = false;
            boolean deleted = false;
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                Query query = session.createQuery("from SpaceMarines");
                list = (List<SpaceMarines>) query.list();

                session.getTransaction().commit();
            } catch (Throwable cause) {
                cause.printStackTrace();
            }
            for (SpaceMarines spaceMarines : list) {
                if (spaceMarines.getKey() == key){
                    exists = true;
                    if (spaceMarines.getUser().equals(login)){
                        if (spaceMarines.getHealth() < health) {
                            spaceMarinesDao.delete(spaceMarines);
                            spaceMarines.setHealth(health);
                            spaceMarinesDao.save(spaceMarines);
                            deleted = true;
                        }
                        if (spaceMarines.getHealth() == health) {
                            message = "New value is equal to old.";
                        }
                        if (spaceMarines.getHealth() > health) {
                            message = "New value is lower than old.";
                        }
                    } else message = "You don't have permission to access this element.";
                }
            }

            if (!exists) message = "No such key exists.";

            if (deleted) {
                for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                    if (key == entry.getKey()) {
                        SpaceMarine test = entry.getValue();
                        if (test.getHealth() < health) {
                            test.setHealth(health);
                            collection.put(key, test);
                            message = "Health value updated successfully.";
                        }
                    }
                }
            }
        } catch (NumberFormatException numberFormatException) {
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }
}
