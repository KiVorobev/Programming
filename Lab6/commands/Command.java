package commands;

import data.Manager;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class from which all commands are inherited
 */
abstract public class Command {

    /** Метод который запускает выполнение команды */
    public Object action(String s) {
        return null;
    }

    public String action(){
        return null;
    }

    public Object action(Manager m) {
        return null;
    }

    public Object action(String s, Manager m) {
        return null;
    }
}