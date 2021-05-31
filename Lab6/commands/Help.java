package commands;

import data.FileWorker;

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
    public String action(FileWorker collection){
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> entry : collection.getInfoCommands().entrySet()) {
            message.append(entry.getKey()).append(entry.getValue()).append("\n").append("\n");
        }
        return message.toString();
    }
}