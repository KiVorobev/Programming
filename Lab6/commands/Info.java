package commands;

import data.FileWorker;

/**
 * Class of command 'info'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Info extends Command {

    /**
     * Method for executing this command
     *
     * @param collection collection
     * @return String description of command
     */
    public String action(FileWorker collection) {
        return "Collection Type: " + collection.getSpaceMarines().getClass() +
                "\nInitialization date: " + collection.getInitializationDate() +
                "\nAmount of elements: " + collection.getSpaceMarines().size();
    }
}