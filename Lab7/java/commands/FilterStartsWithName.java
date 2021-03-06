package commands;

import data.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class of command 'filter_starts_with_name'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class FilterStartsWithName extends Command {

    /**
     * Method for executing this command
     *
     * @param startName start part of name
     * @param collection collection
     * @return String description of command
     */
    public String action(String startName, TreeMap<Integer,SpaceMarine> collection, String login) {
        try {
            String test = startName;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            StringBuilder message = new StringBuilder();
            boolean check = false;
            boolean notEmpty = false;
            ArrayList<Integer> keys = new ArrayList<>();
            for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                if (entry.getValue().getUser().equals(login)) {
                    notEmpty = true;
                    if (entry.getValue().getName().length() >= test.length()) {
                        if (entry.getValue().getName().substring(0, test.length()).equals(test)) {
                            keys.add(entry.getKey());
                            check = true;
                        }
                    }
                }
            }
            if (notEmpty) {
                if (check) {
                    for (int i = 0; i < keys.size(); i++) {
                        for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                            if (keys.get(i) == entry.getKey()) {
                                message.append(entry.getKey() + " " + entry.getValue());
                            }
                        }
                    }
                } else {
                    message.append("There are no items in the collection whose name begins with the given substring.");
                }
            } else message.append("Your collection is empty.");
            return message.toString();
        } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            return "The word cannot start from spaces.";
        }
    }
}