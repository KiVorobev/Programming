package commands;

import database.DataBase;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class from which all commands are inherited
 */
abstract public class Command {

    /** Method for commands execution */
    public Object action(String s) {
        return null;
    }
    /** Method for commands execution */
    public String action(){
        return null;
    }
    /** Method for commands execution */
    public Object action(DataBase m) {
        return null;
    }
    /** Method for commands execution */
    public Object action(String s, DataBase m) {
        return null;
    }
}