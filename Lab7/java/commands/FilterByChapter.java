package commands;

import data.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

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
    public String action(String chapterName, TreeMap<Integer,SpaceMarine> collection, String login) {
        String test = chapterName.trim();
        StringBuilder message = new StringBuilder();
        boolean check = false;
        boolean notEmpty = false;
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
            if (entry.getValue().getUser().equals(login)) {
                notEmpty = true;
                if (entry.getValue().getChapter().getChapterName().equals(test)) {
                    keys.add(entry.getKey());
                    check = true;
                }
            }
        }
        if (notEmpty) {
            if (check) {
                for (int i = 0; i < keys.size(); i++) {
                    for (Map.Entry<Integer, SpaceMarine> entry : collection.entrySet()) {
                        if (keys.get(i) == entry.getKey()) {
                            message.append(entry.getKey() + " " + entry.getValue());
                        }
                    }
                }
            } else {
                message.append("There are no items in the collection with this value.");
            }
        } else message.append("Your collection is empty.");
        return message.toString();
    }
}