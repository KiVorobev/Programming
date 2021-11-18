package commands;

import data.FileWorker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class of command 'execute_script'
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class ExecuteScript extends Command {

    /** Method for executing script from external file */
    public String action(String nameOfFile, FileWorker fileWorker) {
        try {
            String[] newNameOfFile = nameOfFile.trim().split(" ", 2);
            BufferedReader reader = new BufferedReader(new FileReader(newNameOfFile[0]));
            StringBuilder message = new StringBuilder();
            String[] cleanUserCommand;
            String command;
                message.append("WARNING! To avoid recursion, your file cannot contain execute script commands.\n" +
                        "-----------------------------------------------------------------------------------\n");
                while ((command = reader.readLine()) != null) {
                    cleanUserCommand = command.trim().toLowerCase().split(" ", 2);
                    switch (cleanUserCommand[0]) {
                        case "":
                            message.append("Command cannot be empty.\n" +
                                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "help":
                            message.append(new Help().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'help' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "info":
                            message.append(new Info().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'info' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "show":
                            message.append(new Show().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'show' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "insert":
                            if (cleanUserCommand.length == 2) {
                                message.append(new Insert().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'insert " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "update":
                            if (cleanUserCommand.length == 2) {
                                message.append(new Update().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'update " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "remove_key":
                            if (cleanUserCommand.length == 2) {
                                message.append(new RemoveKey().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'remove_key " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "exit":
                            message.append(new Exit().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'exit' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "clear":
                            message.append(new Clear().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'clear' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "execute_script":
                            if (cleanUserCommand.length == 2) {
                                System.out.println(FileWorker.getPaths());
                                if (!(FileWorker.getPaths().contains(cleanUserCommand[1].toLowerCase()))) {
                                    FileWorker.getPaths().add(cleanUserCommand[1]);
                                    message.append(new ExecuteScript().action(cleanUserCommand[1], fileWorker) + "\n")
                                            .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                                                    + "Command 'execute_script " + cleanUserCommand[1] + "' is ended.\n" +
                                                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                                } else {
                                    message.append("Recursion has been found in file: " + cleanUserCommand[1] +
                                            "\nPlease correct your script!\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                                }
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "remove_greater":
                            if (cleanUserCommand.length == 2) {
                                message.append(new RemoveGreater().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'remove_greater " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "replace_if_greater":
                            if (cleanUserCommand.length == 2) {
                                message.append(new ReplaceIfGreater().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'replace_if_greater " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "remove_greater_key":
                            if (cleanUserCommand.length == 2) {
                                message.append(new RemoveGreaterKey().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'remove_greater_key " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "group_counting_by_coordinates":
                            message.append(new GroupCountingByCoordinates().action(fileWorker) + "\n")
                                    .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                            + "\nCommand 'group_counting_by_coordinates' is ended.\n" +
                                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                        case "filter_by_chapter":
                            if (cleanUserCommand.length == 2) {
                                message.append(new FilterByChapter().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'filter_by_chapter " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        case "filter_starts_with_name":
                            if (cleanUserCommand.length == 2) {
                                message.append(new FilterStartsWithName().action(cleanUserCommand[1], fileWorker) + "\n")
                                        .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                                                + "\nCommand 'filter_starts_with_name " + cleanUserCommand[1] + "' is ended.\n" +
                                                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            } else message.append("This command needs an argument.\n");
                            break;
                        default:
                            message.append("Unknown command.\n" +
                                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            break;
                    }
                }
                reader.close();
                message.append("Commands are ended.");
                return message.toString();
            } catch(FileNotFoundException fileNotFoundException){
                return "File not found. Try again.";
            } catch(IOException ioException){
                return "File reading exception. Try again.";
        }
    }
}