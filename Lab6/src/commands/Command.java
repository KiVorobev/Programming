package commands;

import data.Manager;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class from which all commands are inherited
 */
abstract public class Command {
    /** Метод который запускает выполнение команды */
    public static Object action(){
        return null;
    }
}