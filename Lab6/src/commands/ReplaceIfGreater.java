package commands;

import data.FileWorker;
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
    public String action(String in, FileWorker collection) {
        String message = null;
        try {
            String[] newElement = in.trim().split("\n", 2);
            int key = Integer.parseInt(newElement[0]);
            int health = Integer.parseInt(newElement[1]);
            boolean check = false;
            for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                if (key == entry.getKey()) {
                    check = true;
                    if (entry.getValue().getHealth() < health) {
                        entry.setValue(entry.getValue()).setHealth(health);
                        collection.save();
                        message = "Health value updated successfully.";
                    } else if (entry.getValue().getHealth() == health) {
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
