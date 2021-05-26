package commands;

import data.*;

import java.time.LocalDateTime;
import java.util.Map;

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
     * @param manager collection
     * @return String description of command
     */
    public String action(String element, Manager manager) {
        String[] newElement = element.trim().split("\n", 11);
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
        SpaceMarine finishSpaceMarine = new SpaceMarine(makeId(manager), newElement[1], newCord, newElement[4],
                Integer.parseInt(newElement[5]), newCat, newWeapon, newMelee, newChap);
        if (!manager.getSpaceMarines().containsKey(Integer.parseInt(newElement[0]))) {
            int key = Integer.parseInt(newElement[0]);
            finishSpaceMarine.setId(makeId(manager));
            manager.getSpaceMarines().put(key, finishSpaceMarine);
            manager.save();
            return "Element added successfully.";
        } else {
            return "Such key already exists. Try again.";
        }
    }

    /**
     * Method for receiving ID of element
     *
     * @param manager collection
     * @return int ID
     */
    public int makeId(Manager manager) {
        int maxId = 0;
        for (Map.Entry<Integer, SpaceMarine> marines : manager.getSpaceMarines().entrySet()) {
            if (marines.getValue().getId() > maxId) {
                maxId = marines.getValue().getId();
            }
        }
        return maxId + 1;
    }
}