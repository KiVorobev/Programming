package commands;

import data.*;

import java.util.Map;
import java.util.TreeMap;

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
    public String action(String in, TreeMap<Integer,SpaceMarine> collection) {
        String message = null;
        try {
            String[] newElement = in.trim().split("\n", 2);
            int key = Integer.parseInt(newElement[0]);
            int health = Integer.parseInt(newElement[1]);
            boolean check = false;
            for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                if (key == entry.getKey()) {
                    check = true;
                    SpaceMarine test = entry.getValue();
                    if (test.getHealth() < health) {
                        test.setHealth(health);
                        collection.put(key, test);
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
            message = "Argument must be of type integer. Try again.";
        }
        return message;
    }
}
