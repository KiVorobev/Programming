package commands;


import data.FileWorker;
import data.SpaceMarine;

import java.util.ArrayList;
import java.util.Map;

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
    public String action(String startName, FileWorker collection) {
        try {
            String test = startName;
            while (test.substring(0, 1).equals(" ")) {
                test = test.substring(1, test.length());
            }
            StringBuilder message = new StringBuilder();
            boolean check = false;
            ArrayList<Integer> keys = new ArrayList<>();
            for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                SpaceMarine exMarine = entry.getValue();
                if (exMarine.getName().length() >= test.length()) {
                    if (exMarine.getName().substring(0, test.length()).equals(test)) {
                        keys.add(entry.getKey());
                        check = true;
                    }
                }
            }
            if (check) {
                for (int i = 0; i < keys.size(); i++) {
                    for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                        if (keys.get(i) == entry.getKey()) {
                            message.append(entry.getKey() + " " + entry.getValue());
                        }
                    }
                }
            } else {
                message.append("There are no items in the collection whose name begins with the given substring.");
            }
            return message.toString();
        } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            return "The word cannot start from spaces.";
        }
    }
}