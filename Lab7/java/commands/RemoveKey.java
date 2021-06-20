package commands;

import data.*;
import database.HibernateSessionFactoryUtil;
import database.SpaceMarines;
import database.SpaceMarinesDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.TreeMap;

/**
 * Class of command 'remove_key'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class RemoveKey extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @return - String description of command
     */
    public String action(String in, TreeMap<Integer,SpaceMarine> collection, String login) {
        StringBuilder message = new StringBuilder();
        try {
            String test = in;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            int key;
            if (test.indexOf(" ") > 0) {
                key = Integer.parseInt(test.substring(0, test.indexOf(" ")));
            } else {
                key = Integer.parseInt(test);
            }

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
                        spaceMarinesDao.delete(spaceMarines);
                        deleted = true;
                    } else message.append("You don't have permission to access this element.");
                }
            }
            if (deleted){
                collection.remove(key);
                message.append("Element removed successfully.");
            }
            if (!exists){
                message.append ("An element with this key does not exist.");
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            message.append("Argument must be of type integer. Try again.");
        }
        return message.toString();
    }
}
