package commands;

import data.Manager;
import data.SpaceMarine;

/**
 * Class of command 'update'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Update extends Command {

    /**
     * Method for executing this command
     *
     * @param in - number of id
     * @param collection - collection
     * @return - String description of command
     */
    public String action(String in, Manager collection) {
        try {
            int id = Integer.parseInt(in.substring(1, Integer.parseInt(in.substring(0,1))+1));
            boolean check = false;
            for (SpaceMarine spaceMarine : collection.getSpaceMarines().values()) {
                if (id == spaceMarine.getId()) {
                    spaceMarine.setName(in.substring(Integer.parseInt(in.substring(0,1))+1, in.length()).trim());
                    check = true;
                    collection.save();
                }
            }
            if (!check) {
                return "An element with this id does not exist.";
            } else {
                return "Name of element updated successfully.";
            }
        } catch (NumberFormatException numberFormatException) {
            return "As an argument you need to enter a number.";
        }
    }
}
