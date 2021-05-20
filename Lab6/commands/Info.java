package commands;

import data.Manager;

public class Info extends Command {

    /** Method for printing information about the collection */
    public String action(Manager collection) {
        String message = "Collection Type: " + collection.getSpaceMarines().getClass() +
                "\nInitialization date: " + collection.getInitializationDate() +
                "\nAmount of elements: " + collection.getSpaceMarines().size();
        return message;
    }
}
