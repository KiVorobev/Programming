package commands;

import data.Manager;
import data.SpaceMarine;

import java.util.Map;

/**
 * Class of command 'show'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Show extends Command {

    /**
     * Method for executing this command
     *
     * @param collection - collection
     * @return - String description of command
     */
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