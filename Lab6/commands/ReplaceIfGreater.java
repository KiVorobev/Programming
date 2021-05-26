package commands;

import data.Manager;
import data.SpaceMarine;
import java.util.Map;

/**
 * Class of command 'replace_if_greater'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class ReplaceIfGreater extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @return - String description of command
     */
    public String action(String in, Manager collection) {
        String message = null;
        try {
            String[] newElement = in.trim().split("\n", 2);
            int key = Integer.parseInt(newElement[0]);
            int health = Integer.parseInt(newElement[1]);
            boolean check = false;
            for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                if (key == entry.getKey()) {
                    check = true;
                    SpaceMarine test = entry.getValue();
                    if (test.getHealth() < health) {
                        test.setHealth(health);
                        collection.getSpaceMarines().put(key, test);
                        collection.save();
                        message = "Health value updated successfully.";
                    } else if (test.getHealth() == health) {
                        message = "New value is equal to old.";
                    } else {
                        message = "New value is lower than old.";
                    }
                }
            }
            if (!check) {
                message = "No such key exists.";
            }
        } catch (NumberFormatException numberFormatException) {
            message = "As an argument you need to enter a number.";
        }
        return message;
    }
}
