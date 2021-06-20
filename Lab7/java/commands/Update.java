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
 * Class of command 'update'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Update extends Command {

    /**
     * Method for executing this command
     *
     * @param element number of id
     * @param collection collection
     * @return String description of command
     */
    public String action(String element, TreeMap<Integer,SpaceMarine> collection, String login) {
        String message = null;
        try {
            String[] newElement = element.trim().split("\n", 12);
            Coordinates newCord = new Coordinates(Integer.parseInt(newElement[2]), Integer.parseInt(newElement[3]));
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
            Chapter newChap = new Chapter(newElement[9], newElement[10]);
            int id = Integer.parseInt(newElement[0]);

            SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
            boolean exist = false;
            boolean updated = false;
            List<SpaceMarines> list = null;
            try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
                session.beginTransaction();

                Query query = session.createQuery("from SpaceMarines");
                list = (List<SpaceMarines>) query.list();

                session.getTransaction().commit();
            } catch (Throwable cause) {
                cause.printStackTrace();
            }
            for (SpaceMarines spaceMarines : list) {
                if (spaceMarines.getId() == id) {
                    exist = true;
                    if (spaceMarines.getUser().equals(login)){
                        updated = true;
                        SpaceMarines newSpaceMarine = new SpaceMarines(spaceMarines.getKey(), id, newElement[1],
                                Integer.parseInt(newElement[2]), Integer.parseInt(newElement[3]), 
                                spaceMarines.getCreationDate(), Integer.parseInt(newElement[5]), newCat, newWeapon,
                                newMelee, newElement[9], newElement[10], newElement[11]);
                        spaceMarinesDao.delete(spaceMarines);
                        spaceMarinesDao.save(newSpaceMarine);
                    }
                }
            }
            
            if (!exist) message = "An element with this id does not exist.";
            if (exist && !updated) message = "You don't have permission to access this element.";
                if (updated) {
                    for (SpaceMarine spaceMarine : collection.values()) {
                        if (id == spaceMarine.getId()) {
                            spaceMarine.setName(newElement[1]);
                            spaceMarine.setCoordinates(newCord);
                            spaceMarine.setHealth(Integer.parseInt(newElement[5]));
                            spaceMarine.setCategory(newCat);
                            spaceMarine.setWeaponType(newWeapon);
                            spaceMarine.setMeleeWeapon(newMelee);
                            spaceMarine.setChapter(newChap);
                            spaceMarine.setUser(newElement[11]);
                            message = "Element updated successfully.";
                        }
                    }
                }
        } catch (NumberFormatException numberFormatException) {
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }
}