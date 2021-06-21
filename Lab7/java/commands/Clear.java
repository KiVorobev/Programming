package commands;

import database.SpaceMarines;
import database.SpaceMarinesDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of command 'clear'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class Clear extends Command {

    /**
     * Method for executing this command
     *
     * @return String description of command
     */
    public String action(String login) {
        boolean wasDeleted = false;
        String message;
        SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
        List<SpaceMarines> collect = new ArrayList<SpaceMarines>(spaceMarinesDao.findAll()) {
        };
        if (collect != null && !collect.isEmpty()) {
            for (SpaceMarines spaceMarine : collect) {
                if (spaceMarine.getUser().equals(login)) {
                    try {
                        spaceMarinesDao.delete(spaceMarine);
                    } catch (Exception exception) {
                        return "Error occurred while removing an element.";
                    }
                    wasDeleted = true;
                }
            }
        }

        if (!wasDeleted) message = "Your collection is empty.";
        else message = "Your collection successfully cleared.";

        return message;
    }
}