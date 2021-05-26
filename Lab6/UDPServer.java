import commands.*;
import data.Manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class for starting a server
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class UDPServer {
    /**
     * Server UDP socket running on this port
     */
    public final static int servicePort = 2828;
    /**
     * Client UDP socket running on this port
     */
    public static int port;
    /**
     * Socket for communication
     */
    private static DatagramSocket socket;
    /**
     * Field for storing client commands
     */
    private static String[] userCommand;
    /**
     * Map for printing available commands for user
     */
    private static HashMap<String, Command> commands;
    /**
     * Field for storing the IP-address
     */
    private static InetAddress address;
    /**
     * Array of bytes for organizing a packet for receiving data from client
     */
    static byte[] buffer = new byte[65535];
    /**
     * Array of bytes for organizing a packet for sending answer to client
     */
    static byte[] secondBuffer = new byte[65535];

    /**
     * Server entry point
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Manager startManager = new Manager(args[0]);
            System.out.println("Starting a server module.");
            while (true) {
                socket = new DatagramSocket(servicePort);
                String message = read();
                Manager manager = new Manager(args[0]);
                if (message != null) {
                    write(manager);
                }
                socket.close();
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.out.println("Please, enter a path to XML file.");
        }
    }

    /**
     * Module for receiving messages from the client
     *
     * @return - String client command
     * @throws IOException - receiving exception
     */
    public static String read() throws IOException {
        DatagramPacket fromClientPacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(fromClientPacket);
        address = fromClientPacket.getAddress();
        port = fromClientPacket.getPort();
        String message = new String(fromClientPacket.getData(), 0, fromClientPacket.getLength());
        System.out.println("Received a message: '" + message + "' from the client");
        userCommand = message.trim().split(" ", 2);
        return message;
    }

    /**
     * Module for sending answers to the client
     *
     * @throws IOException          - receiving exception
     * @throws InterruptedException - wait exception
     */
    public static void write(Manager collection) throws IOException, InterruptedException {
        String answer;
        if (userCommand.length == 1) {
            answer = easyExecution(userCommand[0], collection);
        } else if (userCommand.length == 2) {
            answer = hardExecution(userCommand[0], userCommand[1], collection);
        } else answer = "Unknown command. Write 'help' for reference.";
        secondBuffer = answer.getBytes();
        DatagramPacket toClientPacket = new DatagramPacket(secondBuffer, secondBuffer.length, address, port);
        socket.send(toClientPacket);
    }

    /**
     * Module for execution of commands without argument
     *
     * @return - String description of command
     */
    public static String easyExecution(String firstArg, Manager manager) {
        String answer;
        switch (firstArg) {
            case "":
                answer = "";
                break;
            case "help":
                answer = new Help().action(manager);
                break;
            case "info":
                answer = new Info().action(manager);
                break;
            case "show":
                answer = new Show().action(manager);
                break;
            case "exit":
                answer = new Exit().action(manager);
                break;
            case "clear":
                answer = new Clear().action(manager);
                break;
            case "group_counting_by_coordinates":
                answer = new GroupCountingByCoordinates().action(manager);
                break;
            case "insert":
                return "Please, enter number_of_key.";
            case "update":
                return "Please, enter id.";
            case "remove_key":
                return "Please, enter number_of_key.";
            case "execute_script":
                return "Please, enter a path to the script file.";
            case "remove_greater":
                return "Please, enter value_of_health.";
            case "replace_if_greater":
                return "Please, enter number_of_key.";
            case "remove_greater_key":
                return "Please, enter number_of_key.";
            case "filter_by_chapter":
                return "Please, enter chapter_name.";
            case "filter_starts_with_name":
                return "Please, enter a name.";
            default:
                answer = "Unknown command. Write 'help' for reference.";
                break;
        }
        return answer;
    }

    /**
     * Module for execution of commands with argument
     *
     * @return - String description of command
     */
    public static String hardExecution(String firstArg, String secondArg, Manager manager) {
        String answer;
        switch (firstArg) {
            case "":
                answer = "";
                break;
            case "help":
                answer = new Help().action(manager);
                break;
            case "info":
                answer = new Info().action(manager);
                break;
            case "show":
                answer = new Show().action(manager);
                break;
            case "exit":
                answer = new Exit().action(manager);
                break;
            case "clear":
                answer = new Clear().action(manager);
                break;
            case "group_counting_by_coordinates":
                answer = new GroupCountingByCoordinates().action(manager);
                break;
            case "insert":
                answer = new Insert().action(secondArg, manager);
                break;
            case "update":
                answer = new Update().action(secondArg, manager);
                break;
            case "remove_key":
                answer = new RemoveKey().action(secondArg, manager);
                break;
            case "execute_script":
                Manager.getPaths().add(secondArg.toLowerCase());
                answer = new ExecuteScript().action(secondArg, manager);
                Manager.getPaths().clear();
                break;
            case "remove_greater":
                answer = new RemoveGreater().action(secondArg, manager);
                break;
            case "replace_if_greater":
                answer = new ReplaceIfGreater().action(secondArg, manager);
                break;
            case "remove_greater_key":
                answer = new RemoveGreaterKey().action(secondArg, manager);
                break;
            case "filter_by_chapter":
                answer = new FilterByChapter().action(secondArg, manager);
                break;
            case "filter_starts_with_name":
                answer = new FilterStartsWithName().action(secondArg, manager);
                break;
            default:
                answer = "Unknown command. Write 'help' for reference.";
                break;
        }
        return answer;
    }
}