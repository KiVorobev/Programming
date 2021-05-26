package commands;

import data.Manager;
import data.SpaceMarine;

/**
 * Class of command 'filter_by_chapter'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class FilterByChapter extends Command {

    /**
     * Method for executing this command
     *
     * @param chapterName name of chapter
     * @param collection collection
     * @return String description of command
     */
    public String action(String chapterName, Manager collection) {
        String test = chapterName.trim();
        StringBuilder message = new StringBuilder();
        boolean check = false;
        for (SpaceMarine spaceMarine : collection.getSpaceMarines().values()) {
            if (spaceMarine.getChapter().getChapterName().equals(test)) {
                message.append(spaceMarine.toString());
                check = true;
            }
        }
        if (!check) {
            message.append("There are no items in the collection with this value.");
        }
        return message.toString();
    }
}