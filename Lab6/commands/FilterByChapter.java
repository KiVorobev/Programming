package commands;

import data.FileWorker;
import data.SpaceMarine;

import java.util.ArrayList;
import java.util.Map;

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
    public String action(String chapterName, FileWorker collection) {
        String test = chapterName.trim();
        StringBuilder message = new StringBuilder();
        boolean check = false;
        ArrayList<Integer> keys = new ArrayList<>();
        for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
            SpaceMarine exMarine = entry.getValue();
            if (exMarine.getChapter().getChapterName().equals(test)) {
                keys.add(entry.getKey());
                check = true;
            }
        }
        if (check) {
            for (int i = 0; i < keys.size(); i++) {
                for (Map.Entry<Integer, SpaceMarine> entry : collection.getSpaceMarines().entrySet()) {
                    if (keys.get(i) == entry.getKey()) {
                        message.append(entry.getKey() + " " + entry.getValue());
                    }
                }
            }
        } else {
            message.append("There are no items in the collection with this value.");
        }
        return message.toString();
    }
}