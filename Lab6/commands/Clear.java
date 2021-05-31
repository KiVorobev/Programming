package commands;

import data.FileWorker;

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
    public String action(FileWorker collection){
        String message;
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