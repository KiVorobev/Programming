package commands;

import data.SpaceMarine;
import database.SpaceMarines;
import database.SpaceMarinesDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public String action(TreeMap<Integer, SpaceMarine> collection, String login) {
        boolean wasDeleted = false;
        String message = "q";
        SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
        TreeMap<Integer, SpaceMarine> userCollection = null;
        List<SpaceMarines> collect = new ArrayList<SpaceMarines>(spaceMarinesDao.findAll()) {
        };
        if (collect != null && !collect.isEmpty()) {
            for (SpaceMarines spaceMarine : collect) {
                if (spaceMarine.getUser().equals(login)) {
                    spaceMarinesDao.delete(spaceMarine);
                    wasDeleted = true;
                }
            }
        }
        List<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, SpaceMarine> spaceMarineEntry : collection.entrySet()) {
            if (spaceMarineEntry.getValue().getUser().equals(login)) {
                System.out.println(spaceMarineEntry.getKey());
                keys.add(spaceMarineEntry.getKey());
            }
        }
        for (Integer delete : keys) {
            collection.remove(delete);
        }
        if (!wasDeleted) message = "Your collection is empty.";
        else message = "Your collection successfully cleared.";

        return message;
    }
}