package commands;

import data.Manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScript extends Command {

    /** Method for executing script from external file */
    public String action(String nameOfFile, Manager manager) {
        try {
            String[] newNameOfFile = nameOfFile.trim().split(" ", 2);
            System.out.println("WARNING! To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(newNameOfFile[0]));
            StringBuilder message = new StringBuilder();
            String[] cleanUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                cleanUserCommand = command.trim().toLowerCase().split(" ", 2);
                switch (cleanUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        message.append(new Help().action(manager) + "\n");
                        break;
                    case "info":
                        message.append(new Info().action(manager) + "\n");
                        break;
                    case "show":
                        message.append(new Show().action(manager) + "\n");
                        break;
                    case "insert":
                        if (cleanUserCommand.length == 2) {
                            message.append(new Insert().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "update":
                        if (cleanUserCommand.length == 2) {
                        message.append(new Update().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "remove_key":
                        if (cleanUserCommand.length == 2) {
                        message.append(new RemoveKey().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "exit":
                        message.append(new Exit().action(manager) + "\n");
                        break;
                    case "clear":
                        message.append(new Clear().action(manager) + "\n");
                        break;
                    case "execute_script":
                        message.append("This script cannot to contain this command.\n");
                        break;
                    case "remove_greater":
                        if (cleanUserCommand.length == 2) {
                        message.append(new RemoveGreater().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "replace_if_greater":
                        if (cleanUserCommand.length == 2) {
                        message.append(new ReplaceIfGreater().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "remove_greater_key":
                        if (cleanUserCommand.length == 2) {
                        message.append(new RemoveGreaterKey().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "group_counting_by_coordinates":
                        message.append(new GroupCountingByCoordinates().action(manager) + "\n");
                        break;
                    case "filter_by_chapter":
                        if (cleanUserCommand.length == 2) {
                        message.append(new FilterByChapter().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    case "filter_starts_with_name":
                        if (cleanUserCommand.length == 2) {
                        message.append(new FilterStartsWithName().action(cleanUserCommand[1], manager) + "\n");
                        } else message.append("This command needs an argument.\n");
                        break;
                    default:
                        message.append("\nUnknown command.\n");
                        break;
                }
                message.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                             + "\nCommand is ended.\n" +
                               "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
            reader.close();
            message.append("Commands are ended.");
            return message.toString();
        } catch (FileNotFoundException fileNotFoundException) {
            return "File not found. Try again.";
        } catch (IOException ioException) {
            return "File reading exception. Try again.";
        }
    }
}
