import commands.*;
import data.Manager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class UDPServer {
    /** Server UDP socket running on this port */
    public final static int servicePort = 2828;
    public static int port;
    /** Socket for communication */
    private static DatagramSocket socket;
    private static String[] userCommand;
    private static HashMap<String, Command> commands;
    private static InetAddress address;
    static byte[] buffer = new byte[65535];
    static byte[] secondBuffer = new byte[65535];



    UDPServer() {
        try {
            socket = new DatagramSocket(servicePort);
        } catch (SocketException socketException) {
            socketException.printStackTrace();
        }
        commands = new HashMap<>();
        commands.put("help", new Help());
        commands.put("info", new Info());
        commands.put("show", new Show());
        commands.put("insert number_of_key", new Insert());
        commands.put("update id", new Update());
        commands.put("remove_key number_of_key", new RemoveKey());
        commands.put("clear", new Clear());
        commands.put("execute_script file_name", new ExecuteScript());
        commands.put("exit", new Exit());
        commands.put("remove_greater value_of_health", new RemoveGreater());
        commands.put("replace_if_greater value_of_health", new ReplaceIfGreater());
        commands.put("remove_greater_key number_of_key", new RemoveGreaterKey());
        commands.put("group_counting_by_coordinates", new GroupCountingByCoordinates());
        commands.put("filter_by_chapter chapter_name", new FilterByChapter());
        commands.put("filter_starts_with_name name", new FilterStartsWithName());
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Manager manager = new Manager(args[0]);
            socket = new DatagramSocket(servicePort);
            System.out.println("Starting a server module.");
                while (true) {
                    String message = read();
                    if (message != null) {
                        write(manager);
                    }
                }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
        }
    }

    /**
     * Модуль чтения запроса
     *
     * @return - возвращает десериализованную команду
     * @throws IOException - ошибка чтения запроса
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
     * Модуль отправки ответов клиенту
     *
     * @throws IOException - ошибка чтения запроса
     * @throws InterruptedException - ошибка ожидания
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

    /** Модуль выполнения команд */
    public static String easyExecution(String firstArg, Manager manager){
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

    /** Модуль выполнения команд */
    public static String hardExecution(String firstArg, String secondArg, Manager manager){
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
                answer = new ExecuteScript().action(secondArg, manager);
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