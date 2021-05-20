package commands;

import data.Manager;
import data.SpaceMarine;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RemoveGreaterKey extends Command {

    /** Method for removing elements from collection if it`s value of key more than entered value */
    public String action(String in, Manager collection) {
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
            message = "As an argument you need to enter a number.";
        }
        collection.save();
        return message;
    }
}
