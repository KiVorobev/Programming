import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Kirill Vorobyev
 * @version 1.1
 * Method for handling user`s command
 */
public class Commander {

    /** Field for receiving user`s command */
    private String userCommand;
    /** Field for separating user input into a command and an argument to it */
    private String[] cleanUserCommand;
    /** Collection manager for realising user`s commands */
    private CollectionManagement manager;

    {
        userCommand = " ";
    }

    /**
     * Constructor for making a commander
     * @param manager - CollectionManager class object which will realise user`s commands
     */
    public Commander (CollectionManagement manager){
        this.manager = manager;
    }

    /** Method for starting console mod */
    public void consoleMod() {
        try {
            Scanner commandReader = new Scanner(System.in);
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                //cleanUserCommand = userCommand.trim().split(" ", 2);
                //Regular expression to match space(s)
                String regex = "\\s+";
                //Replacing the pattern with single space
                String notCleanUserCommand = userCommand.replaceAll(regex, " ");
                cleanUserCommand = notCleanUserCommand.trim().split(" ", 2);
                switch (cleanUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        if (cleanUserCommand.length == 1) {
                            manager.help();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "info":
                        if (cleanUserCommand.length == 1) {
                            manager.info();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "show":
                        if (cleanUserCommand.length == 1) {
                            manager.show();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "insert":
                        if (cleanUserCommand.length == 2) {
                            manager.insert(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "update":
                        if (cleanUserCommand.length == 2) {
                            manager.update(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "remove_key":
                        if (cleanUserCommand.length == 2) {
                            manager.removeKey(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "clear":
                        if (cleanUserCommand.length == 1) {
                            manager.clear();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "save":
                        if (cleanUserCommand.length == 1) {
                            manager.save();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "execute_script":
                        if (cleanUserCommand.length == 2) {
                            manager.executeScript(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "exit":
                        if (cleanUserCommand.length == 1) {
                            manager.exit();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "remove_greater":
                        if (cleanUserCommand.length == 1) {
                            System.out.println("Enter characteristics of element, which will be compared with elements in collection.");
                            manager.removeGreater(manager.scanHealth());
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "replace_if_greater":
                        if (cleanUserCommand.length == 2) {
                            manager.replaceIfGreater(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "remove_greater_key":
                        if (cleanUserCommand.length == 2) {
                            manager.removeGreaterKey(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    case "group_counting_by_category":
                        if (cleanUserCommand.length == 1) {
                            manager.groupCountingByCategory();
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "filter_by_chapter":
                        if (cleanUserCommand.length == 1) {
                            manager.filterByChapter(manager.scanChapter());
                        } else {
                            System.out.println("No arguments are needed for this command.");
                        }
                        break;
                    case "filter_starts_with_name":
                        if (cleanUserCommand.length == 2) {
                            manager.filterStartsWithName(cleanUserCommand[1]);
                        } else if (cleanUserCommand.length == 1) {
                            System.out.println("This command needs an argument.");
                        } else if (cleanUserCommand.length > 2) {
                            System.out.print("This command only needs one argument.");
                        }
                        break;
                    default:
                        System.out.println("Unknown command. Write 'help' for reference.");
                }
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("You clicked shortcut for finishing a program.");
            System.exit(0);
        }
    }
}
