package commands;

import data.AstartesCategory;
import data.MeleeWeapon;
import data.SpaceMarine;
import data.Weapon;
import database.DataBase;
import database.HibernateSessionFactoryUtil;
import database.SpaceMarines;
import database.SpaceMarinesDao;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.TreeMap;

/**
 * Class of command 'insert'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Insert extends Command {

    /**
     * Method for executing this command
     *
     * @param element SpaceMarine element
     * @param collection collection
     * @param dataBase data base
     *
     * @return String description of command
     */
    public String action(String element, TreeMap<Integer, SpaceMarine> collection, DataBase dataBase) {
        SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
        boolean exist = false;
        String[] newElement = element.trim().split("\n", 12);

        List<SpaceMarines> list = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from SpaceMarines");
            list = (List<SpaceMarines>) query.list();

            session.getTransaction().commit();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        for (SpaceMarines spaceMarines : list){
            if (spaceMarines.getKey() == Integer.parseInt(newElement[0])) exist = true;
        }

        if (!exist) {
        AstartesCategory newCat = null;
        Weapon newWeapon = null;
        MeleeWeapon newMelee = null;
        if (!newElement[6].equals("null")) {
            newCat = AstartesCategory.valueOf(newElement[6]);
        }
        if (!newElement[7].equals("null")) {
            newWeapon = Weapon.valueOf(newElement[7]);
        }
        if (!newElement[8].equals("null")) {
            newMelee = MeleeWeapon.valueOf(newElement[8]);
        }
            int key = Integer.parseInt(newElement[0]);

        SpaceMarines finishSpaceMarines = new SpaceMarines(key, 0,
                newElement[1], Integer.parseInt(newElement[2]), Integer.parseInt(newElement[3]), newElement[4], Integer.parseInt(newElement[5]), newCat,
                newWeapon, newMelee, newElement[9], newElement[10], newElement[11]);
        try {
            spaceMarinesDao.save(finishSpaceMarines);
            dataBase.loadCollection(collection);
        } catch (Exception exception) {
            return "Error occurred while adding an element.";
            }
            return "Element added successfully.";
        } else {
            return "Such key already exists. Try again.";
        }
    }
}