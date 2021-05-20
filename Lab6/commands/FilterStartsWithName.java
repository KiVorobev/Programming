package commands;


import data.Manager;
import data.SpaceMarine;

public class FilterStartsWithName extends Command {

    /**
     * Method for printing elements whose name start from it's letters
     */
    public String action(String startName, Manager collection) {
        try {
            String test = startName;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            StringBuilder message = new StringBuilder();
            boolean check = false;
            for (SpaceMarine spaceMarine : collection.getSpaceMarines().values()) {
                if (spaceMarine.getName().substring(0, test.length()).equals(test)) {
                    message.append(spaceMarine);
                    check = true;
                }
            }
            if (!check) {
                message.append("There are no items in the collection whose name begins with the given substring.");
            }
            return message.toString();
        } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            return "The word cannot start from spaces.";
        }
    }
}
