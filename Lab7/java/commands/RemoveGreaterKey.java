package commands;

import data.*;
import database.DataBase;
import database.HibernateSessionFactoryUtil;
import database.SpaceMarines;
import database.SpaceMarinesDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.*;

/**
 * Class of command 'remove_greater_key'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class RemoveGreaterKey extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @param login user's login
     * @param dataBase data base
     *
     * @return String description of command
     */
    public String action(String in, TreeMap<Integer,SpaceMarine> collection, String login, DataBase dataBase) {
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
            boolean deleted = false;
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
                if (spaceMarines.getUser().equals(login)) {
                    exists = true;
                    if (spaceMarines.getKey() > key) {
                        try {
                            spaceMarinesDao.delete(spaceMarines);
                            dataBase.loadCollection(collection);
                        } catch (Exception exception) {
                        return "Error occurred while removing an element.";
                    }
                        deleted = true;
                    }
                }
            }
            if (!exists)  message = "Your collection is empty.";
            if (exists && !deleted) message = "There are no elements in the collection that greater the specified one.";
            if (deleted) message = "Element(s) removed successfully.";
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }
}