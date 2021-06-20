package commands;

import data.SpaceMarine;
import database.DataBase;

import java.util.Map;
import java.util.TreeMap;

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
    public String action(TreeMap<Integer, SpaceMarine> collection, String login) {
        int userCollection = 0;
        for (Map.Entry<Integer,SpaceMarine> entry : collection.entrySet()){
            if (entry.getValue().getUser().equals(login)){
                userCollection += 1;
            }
        }

        return "Collection Type: " + collection.getClass() +
                "\nInitialization date: " + DataBase.getInitializationDate() +
                "\nAmount of elements: " + collection.size() +
                "\nAmount of your elements: " + userCollection;
    }
}