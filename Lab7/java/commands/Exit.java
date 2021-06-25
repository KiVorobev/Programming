package commands;

import data.*;
import java.util.TreeMap;

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
    public String action(TreeMap<Integer,SpaceMarine> collection){
        return "Program will be finished now. See you again:)";
    }
}