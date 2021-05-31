package commands;

import data.FileWorker;
import data.SpaceMarine;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class of command 'remove_greater_key'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class RemoveGreaterKey extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @return String description of command
     */
    public String action(String in, FileWorker collection) {
        String message = null;
        try {
            String test = in;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            int key;
            if (test.indexOf(" ") > 0) {
                key = Integer.parseInt(test.substring(0, test.indexOf(" ")));
            } else {
                key = Integer.parseInt(test);
            }
            int counter = 0;
            boolean check = false;
            Set<Integer> keys = new HashSet<>();
            for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                if (key < entry.getKey()) {
                    keys.add(entry.getKey());
                    check = true;
                }
            }
            if (check) {
                for (Integer last : keys) {
                    counter += 1;
                    collection.getSpaceMarines().remove(last);
                }
            }
            if (counter == 0) {
                message = "There are no elements in the collection that exceed the specified one.";
            }
            if (counter == 1) {
                message = "1 element removed successfully.";
            }
            if (counter > 1) {
                message = counter + " elements removed successfully.";
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            message = "Argument must be of type integer. Try again.";
        }
        collection.save();
        return message;
    }
}