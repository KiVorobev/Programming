package commands;

import data.Manager;

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
    public String action(Manager collection){
        String message = null;
        if (collection.getSpaceMarines().isEmpty()){
            message = "Collection is clear.";
        } else {
            collection.getSpaceMarines().clear();
            message = "Collection successfully cleared.";
        }
        collection.save();
        return message;
    }
}