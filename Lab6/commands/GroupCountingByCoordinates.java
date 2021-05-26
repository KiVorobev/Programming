package commands;

import data.Manager;
import data.SpaceMarine;
import java.util.HashMap;
import java.util.Map;

/**
 * Class of command 'group_counting_by_coordinates'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class GroupCountingByCoordinates extends Command {

    /**
     * Method for executing this command
     *
     * @param collection collection
     * @return String description of command
     */
    public String action(Manager collection) {
        StringBuilder message = new StringBuilder();
        Map<String, Integer> cords = new HashMap<>();
        for (SpaceMarine spaceMarine : collection.getSpaceMarines().values()) {
            if (cords.containsKey(spaceMarine.getCoordinates().toString())) {
                cords.put(spaceMarine.getCoordinates().toString(),cords.get(spaceMarine.getCoordinates().toString()) + 1 );
            } else {
                cords.put(spaceMarine.getCoordinates().toString(), 1);
            }
        }
        for (Map.Entry<String, Integer> cord : cords.entrySet()) {
            message.append("Elements with coordinates " + cord.getKey() + " : ");
            message.append(cord.getValue() + "\n");
        }
        return message.toString();
    }
}