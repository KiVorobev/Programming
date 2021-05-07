import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Method for handling user`s command
 */
public class Commander {

    /**
     * Field for receiving user`s command
     */
    private String userCommand;
    /**
     * Field for separating user input into a command and an argument to it
     */
    private String[] cleanUserCommand;
    /**
     * Collection manager for realising user`s commands
     */
    private CollectionManagement manager;

    {
        userCommand = " ";
    }

    /**
     * Constructor for making a commander
     *
     * @param manager - CollectionManager class object which will realise user`s commands
     */
    public Commander(CollectionManagement manager) {
        this.manager = manager;
    }

    /**
     * Method for starting console mod
     */
    public void consoleMod() {
        try {
            Scanner commandReader = new Scanner(System.in);
            while (!userCommand.equals("exit")) {
                System.out.print("Enter a command: ");
                userCommand = commandReader.nextLine();
                //Regular expression to match space(s)
                String regex = "\\s+";
                //Replacing the pattern with single space
                String notCleanUserCommand = userCommand.replaceAll(regex, " ");
                cleanUserCommand = notCleanUserCommand.trim().split(" ", 2);
                try {
                    switch (cleanUserCommand[0]) {
                        case "":
                            break;
                        case "help":
                            manager.help();
                            break;
                        case "info":
                            manager.info();
                            break;
                        case "show":
                            manager.show();
                            break;
                        case "insert":
                            manager.insert(cleanUserCommand[1]);
                            break;
                        case "update":
                            manager.update(cleanUserCommand[1]);
                            break;
                        case "remove_key":
                            manager.removeKey(cleanUserCommand[1]);
                            break;
                        case "clear":
                            manager.clear();
                            break;
                        case "save":
                            manager.save();
                            break;
                        case "execute_script":
                            manager.executeScript(cleanUserCommand[1]);
                            break;
                        case "exit":
                            manager.exit();
                            break;
                        case "remove_greater":
                            System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                            manager.removeGreater(manager.scanHealth());
                            break;
                        case "replace_if_greater":
                            manager.replaceIfGreater(cleanUserCommand[1]);
                            break;
                        case "remove_greater_key":
                            manager.removeGreaterKey(cleanUserCommand[1]);
                            break;
                        case "group_counting_by_coordinates":
                            manager.groupCountingByCoordinates();
                            break;
                        case "filter_by_chapter":
                            manager.filterByChapter(cleanUserCommand[1]);
                            break;
                        case "filter_starts_with_name":
                            manager.filterStartsWithName(cleanUserCommand[1]);
                            break;
                        default:
                            System.out.println("Unknown command. Write 'help' for reference.");
                    }
                } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                    System.out.println("This command needs an argument.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("You clicked shortcut for finishing a program.");
            System.exit(0);
        }
    }
}