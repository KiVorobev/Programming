package commands;

import data.*;

import java.util.Map;
import java.util.TreeMap;

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
    public String action(TreeMap<Integer,SpaceMarine> collection){
        StringBuilder message = new StringBuilder();
        if (!collection.isEmpty()) {
            for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                message.append(entry.getKey() + " " + entry.getValue());
            }
        } else {
            message.append("Collection is empty");
        }
        return message.toString();
    }
}