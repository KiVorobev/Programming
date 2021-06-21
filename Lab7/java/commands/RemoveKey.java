package commands;

import data.SpaceMarine;
import database.DataBase;
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
     * @return - String description of command
     */
    public String action(String in, String login, TreeMap<Integer, SpaceMarine> collection, DataBase dataBase) {
        String message = null;
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
                        try {
                        spaceMarinesDao.delete(spaceMarines);
                        dataBase.loadCollection(collection);
                        } catch (Exception exception) {
                            return "Error occurred while removing an element.";
                        }
                        message = "Element removed successfully.";
                    } else message = "You don't have permission to access this element.";
                }
            }
            if (!exists) message = "An element with this key does not exist.";

        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }
}
