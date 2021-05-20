package commands;

import data.Manager;
import data.SpaceMarine;
import java.util.HashMap;
import java.util.Map;

public class GroupCountingByCoordinates extends Command {

    /** Method for counting amount and grouping elements by it's coordinates */
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
