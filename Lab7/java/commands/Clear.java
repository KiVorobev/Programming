package commands;

import data.SpaceMarine;
import java.util.TreeMap;

/**
 * Class of command 'clear'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Clear extends Command {

    /**
     * Method for executing this command
     *
     * @param collection collection
     * @return String description of command
     */
    public String action(TreeMap<Integer, SpaceMarine> collection){
        String message;
        if (collection.isEmpty()){
            message = "Collection is clear.";
        } else {
            collection.clear();
            message = "Collection successfully cleared.";
        }
        return message;
    }
}