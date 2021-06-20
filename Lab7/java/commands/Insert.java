package commands;

import data.*;
import database.SpaceMarines;
import database.SpaceMarinesDao;

import java.util.Map;
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
     * @return String description of command
     */
    public String action(String element, TreeMap<Integer,SpaceMarine> collection) {
        SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
        String[] newElement = element.trim().split("\n", 12);
        if (!collection.containsKey(Integer.parseInt(newElement[0]))) {
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
            int key = Integer.parseInt(newElement[0]);

        SpaceMarines finishSpaceMarines = new SpaceMarines(key,makeId(collection),
                newElement[1], Integer.parseInt(newElement[2]), Integer.parseInt(newElement[3]), newElement[4], Integer.parseInt(newElement[5]), newCat,
                newWeapon, newMelee, newElement[9], newElement[10], newElement[11]);
        spaceMarinesDao.save(finishSpaceMarines);
        SpaceMarine finishSpaceMarine = new SpaceMarine(makeId(collection), newElement[1], newCord, newElement[4],
                Integer.parseInt(newElement[5]), newCat, newWeapon, newMelee, newChap, newElement[11]);
            collection.put(key, finishSpaceMarine);
            return "Element added successfully.";
        } else {
            return "Such key already exists. Try again.";
        }
    }

    /**
     * Method for receiving ID of element
     *
     * @param collection collection
     * @return int ID
     */
    public int makeId(TreeMap<Integer,SpaceMarine> collection) {
        int maxId = 0;
        for (Map.Entry<Integer, SpaceMarine> marines : collection.entrySet()) {
            if (marines.getValue().getId() > maxId) {
                maxId = marines.getValue().getId();
            }
        }
        return maxId + 1;
    }
}