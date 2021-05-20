package commands;

import data.Manager;

public class Clear extends Command {

    /**
     * Метод который удаляет все элементы из коллекции
     *
     * @param collection - коллекция
     */

    public String action(Manager collection){
        String message = null;
        if (collection.getSpaceMarines().isEmpty()){
            message = "Collection is clear.";
        } else {
            collection.getSpaceMarines().clear();
            message = "Collection successfully cleared.\n";
        }
        collection.save();
        return message;
    }
}
