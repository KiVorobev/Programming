package commands;

import data.Manager;

public class RemoveKey extends Command {

    /**
     * Method for deleting element in the collection by it's key
     */
    public String action(String in, Manager collection) {
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
            return "As an argument you need to enter a number.";
        }
    }
}
