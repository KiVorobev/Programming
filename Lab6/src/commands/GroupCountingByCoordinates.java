package commands;

import data.SpaceMarine;
import java.util.HashMap;
import java.util.Map;

public class GroupCountingByCoordinates extends Command {

    /** Method for counting amount and grouping elements by it's coordinates */
    /*public void groupCountingByCoordinates() {
        Map<String, Integer> cords = new HashMap<>();
        for (SpaceMarine spaceMarine : spaceMarines.values()) {
            if (cords.containsKey(spaceMarine.getCoordinates().toString())) {
                cords.put(spaceMarine.getCoordinates().toString(),cords.get(spaceMarine.getCoordinates().toString()) + 1 );
            } else {
                cords.put(spaceMarine.getCoordinates().toString(), 1);
            }
        }
        for (Map.Entry<String, Integer> cord : cords.entrySet()) {
            System.out.print("Elements with coordinates " + cord.getKey() + " : ");
            System.out.print(cord.getValue() + "\n");
        }
    }*/
}
