package commands;

import data.Manager;

/**
 * Class of command 'exit'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Exit extends Command {

    /**
     * Method for executing this command
     *
     * @param collection - collection
     * @return - String phrase for close client
     */
    public String action(Manager collection){
        collection.save();
        return "close client";
    }
}
