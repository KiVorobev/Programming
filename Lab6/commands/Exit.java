package commands;

import data.FileWorker;

/**
 * Class of command 'exit'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Exit extends Command {

    /**
     * Method for executing this command
     *
     * @param collection collection
     * @return String phrase for close client
     */
    public String action(FileWorker collection){
        collection.save();
        return "Program will be finished now. See you again:)";
    }
}