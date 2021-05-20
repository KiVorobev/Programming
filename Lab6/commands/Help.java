package commands;

import data.Manager;

import java.util.*;

public class Help extends Command {

    public String action(Manager collection){
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, String> entry : collection.getInfoCommands().entrySet()) {
            message.append(entry.getKey() + entry.getValue() + "\n");
        }
        return message.toString();
    }
}
