package commands;

import data.Manager;

public class Exit extends Command {

    public String action(Manager collection){
        collection.save();
        return "close client";
    }
}
