package commands;

import data.Manager;
import data.SpaceMarine;

import java.util.Map;

public class Show extends Command {

    /** Method for printing collection elements into the string representation */
    public String action(Manager collection){
        StringBuilder message = new StringBuilder();
        if (!collection.getSpaceMarines().isEmpty()) {
            for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                message.append(entry.getKey() + " ");
                message.append(entry.getValue());
            }
        } else {
            message.append("Collection is empty");
        }
        return message.toString();
    }
}
