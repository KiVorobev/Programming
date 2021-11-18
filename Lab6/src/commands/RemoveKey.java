package commands;

import data.FileWorker;

/**
 * Class of command 'remove_key'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class RemoveKey extends Command {

    /**
     * Method for executing this command
     *
     * @param in number of key
     * @param collection collection
     * @return - String description of command
     */
    public String action(String in, FileWorker collection) {
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
            if (collection.getSpaceMarines().containsKey(key)) {
                collection.getSpaceMarines().remove(key);
                collection.save();
                return "Element deleted successfully.";
            } else {
                return "An element with this key does not exist.";
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return "Argument must be of type integer. Try again.";
        }
    }
}
