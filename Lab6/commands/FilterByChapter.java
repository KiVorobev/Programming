package commands;

import data.Manager;
import data.SpaceMarine;

public class FilterByChapter extends Command {

    /** Method for printing elements whose chapter field value is equal to it's value of chapter */
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