package commands;

import data.*;

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
    public String action(String element, Manager collection) {
        try {
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
            int id = Integer.parseInt(newElement[0]);
            boolean check = false;
            for (SpaceMarine spaceMarine : collection.getSpaceMarines().values()) {
                if (id == spaceMarine.getId()) {
                    spaceMarine.setName(newElement[1]);
                    spaceMarine.setCoordinates(newCord);
                    spaceMarine.setCreationDate(newElement[4]);
                    spaceMarine.setHealth(Integer.parseInt(newElement[5]));
                    spaceMarine.setCategory(newCat);
                    spaceMarine.setWeaponType(newWeapon);
                    spaceMarine.setMeleeWeapon(newMelee);
                    spaceMarine.setChapter(newChap);
                    check = true;
                    collection.save();
                }
            }
            if (!check) {
                return "An element with this id does not exist.";
            } else {
                return "Element updated successfully.";
            }
        } catch (NumberFormatException numberFormatException) {
            return "Argument must be of type integer. Try again.";
        }
    }
}