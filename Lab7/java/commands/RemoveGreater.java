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
 * Class of command 'remove_greater'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class RemoveGreater extends Command implements Comparator<Integer> {

    /**
     * Method for executing this command
     *
     * @param in value of health
     * @param collection collection
     * @return String description of command
     */
    public String action(String in, TreeMap<Integer,SpaceMarine> collection, String login, DataBase dataBase) {
        String message = null;
        try {
            String test = in;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            int health;
            if (test.indexOf(" ") > 0) {
                health = Integer.parseInt(test.substring(0, test.indexOf(" ")));
            } else {
                health = Integer.parseInt(test);
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
                        if (spaceMarines.getHealth() > health) {
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
                if (deleted) {
                    int counter = 0;
                    boolean check = false;
                    Set<Integer> keys = new HashSet<>();
                    for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                        SpaceMarine test2 = entry.getValue();
                        if (compare(health, test2.getHealth()) > 0 && entry.getValue().getUser().equals(login)) {
                            keys.add(entry.getKey());
                            check = true;
                        }
                    }
                    if (check) {
                        for (Integer last : keys) {
                            counter += 1;
                            collection.remove(last);
                        }
                    }
                    if (counter == 0) {
                        message = "There are no elements in the collection that greater the specified one.";
                    }
                    if (counter == 1) {
                        message = "1 element removed successfully.";
                    }
                    if (counter > 1) {
                        message = counter + " elements removed successfully.";
                    }
                }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }

    @Override
    public int compare(Integer health, Integer elementHealth) {
        return elementHealth.compareTo(health);
    }
}