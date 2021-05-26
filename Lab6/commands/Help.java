package commands;

import data.Manager;

import java.util.*;

/**
 * Class of command 'help'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Help extends Command {

    /**
     * Method for executing this command
     *
     * @param collection collection
     * @return String description of command
     */
    public String action(Manager collection){
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> entry : collection.getInfoCommands().entrySet()) {
            message.append(entry.getKey() + entry.getValue() + "\n");
        }
        return message.toString();
    }
}