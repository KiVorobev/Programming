import commands.*;
import data.FileWorker;
import data.SpaceMarine;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.logging.*;

import static java.util.logging.Level.*;

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
     * Logger
     */
    static final Logger logger = Logger.getLogger(UDPServer.class.getName());
    /**
     * Buffer for collection
     */
    static TreeMap<Integer, SpaceMarine> lastCollection = new TreeMap<>();

    /**
     * Server entry point
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            connection();
            FileWorker startFileWorker = new FileWorker(args[0]);
            if (startFileWorker.isNeedToCreate()){
                logger.severe("Try again.");
                System.exit(0);
            }
            lastCollection = startFileWorker.getSpaceMarines();
            logger.info("Server started.");
            while (true) {
                socket = new DatagramSocket(servicePort);
                String message = read();
                FileWorker fileWorker = new FileWorker(args[0]);
                if (fileWorker.isNeedToCreate()){
                    File file = new File(args[0]);
                    logger.info("A new file has been created in the same path.");
                    fileWorker.setXmlFile(file);
                    fileWorker.setSpaceMarines(lastCollection);
                    fileWorker.save();
                }
                if (message != null) {
                    write(fileWorker);
                    lastCollection = fileWorker.getSpaceMarines();
                }
                socket.close();
            }
        } catch (NoSuchElementException noSuchElementException) {
            logger.severe("Program was stopped successfully.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            logger.severe("Please, enter a path to XML file.");
        } catch (BindException bindException) {
            logger.severe("Server is already running!");
        }
    }

    /**
     * Module for connecting the server to a file with a collection
     *
     * @throws IOException - receiving exception
     */
    public static void connection() throws IOException {
        try {
            Handler fileHandler = new FileHandler("MyLogs");
            class MyFormatter extends Formatter {
                @Override
                public String format(LogRecord record) {
                    Date date = new Date(record.getMillis());
                    int day = date.getDate();
                    String finalDay = String.valueOf(day);
                    if (day < 10) {
                        finalDay = "0" + finalDay;
                    }
                    int month = date.getMonth() + 1;
                    String finalMonth = String.valueOf(month);
                    if (month < 10) {
                        finalMonth = "0" + finalMonth;
                    }
                    int year = date.getYear() + 1900;
                    String finalYear = String.valueOf(year);
                    if (year < 10) {
                        finalYear = "0" + finalYear;
                    }
                    int hours = date.getHours();
                    String finalHours = String.valueOf(hours);
                    if (hours < 10) {
                        finalHours = "0" + finalHours;
                    }
                    int minutes = date.getMinutes();
                    String finalMinutes = String.valueOf(minutes);
                    if (minutes < 10) {
                        finalMinutes = "0" + finalMinutes;
                    }
                    int seconds = date.getSeconds();
                    String finalSeconds = String.valueOf(seconds);
                    if (seconds < 10) {
                        finalSeconds = "0" + finalSeconds;
                    }
                    return "(" + finalDay + "." + finalMonth + "." + finalYear + " " +
                            finalHours + ":" + finalMinutes + ":" + finalSeconds + ") " +
                            record.getLevel() + " [" + record.getLoggerName() + "] : " + record.getMessage() + "\n";
                }
            }
            fileHandler.setFormatter(new MyFormatter());
            logger.addHandler(fileHandler);
            logger.info("Starting a server module...");
        } catch (BindException bindException) {
            logger.severe("Server is already running!");
        } catch (NoSuchElementException noSuchElementException) {
            logger.severe("Program was stopped successfully.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            logger.severe("Please, enter a path to XML file.");
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
        logger.log(INFO,"Received a message: '" + message + "' from the client");
        userCommand = message.trim().split(" ", 2);
        return message;
    }

    /**
     * Module for sending answers to the client
     *
     * @throws IOException          - receiving exception
     * @throws InterruptedException - wait exception
     */
    public static void write(FileWorker collection) throws IOException, InterruptedException {
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
    public static String easyExecution(String firstArg, FileWorker fileWorker) {
        String answer;
        switch (firstArg) {
            case "":
                answer = "";
                break;
            case "help":
                answer = new Help().action(fileWorker);
                break;
            case "info":
                answer = new Info().action(fileWorker);
                break;
            case "show":
                answer = new Show().action(fileWorker);
                break;
            case "exit":
                answer = new Exit().action(fileWorker);
                break;
            case "clear":
                answer = new Clear().action(fileWorker);
                break;
            case "group_counting_by_coordinates":
                answer = new GroupCountingByCoordinates().action(fileWorker);
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
    public static String hardExecution(String firstArg, String secondArg, FileWorker fileWorker) {
        String answer;
        switch (firstArg) {
            case "":
                answer = "";
                break;
            case "help":
                answer = new Help().action(fileWorker);
                break;
            case "info":
                answer = new Info().action(fileWorker);
                break;
            case "show":
                answer = new Show().action(fileWorker);
                break;
            case "exit":
                answer = new Exit().action(fileWorker);
                break;
            case "clear":
                answer = new Clear().action(fileWorker);
                break;
            case "group_counting_by_coordinates":
                answer = new GroupCountingByCoordinates().action(fileWorker);
                break;
            case "insert":
                answer = new Insert().action(secondArg, fileWorker);
                break;
            case "update":
                answer = new Update().action(secondArg, fileWorker);
                break;
            case "remove_key":
                answer = new RemoveKey().action(secondArg, fileWorker);
                break;
            case "execute_script":
                FileWorker.getPaths().add(secondArg.toLowerCase());
                answer = new ExecuteScript().action(secondArg, fileWorker);
                FileWorker.getPaths().clear();
                break;
            case "remove_greater":
                answer = new RemoveGreater().action(secondArg, fileWorker);
                break;
            case "replace_if_greater":
                answer = new ReplaceIfGreater().action(secondArg, fileWorker);
                break;
            case "remove_greater_key":
                answer = new RemoveGreaterKey().action(secondArg, fileWorker);
                break;
            case "filter_by_chapter":
                answer = new FilterByChapter().action(secondArg, fileWorker);
                break;
            case "filter_starts_with_name":
                answer = new FilterStartsWithName().action(secondArg, fileWorker);
                break;
            default:
                answer = "Unknown command. Write 'help' for reference.";
                break;
        }
        return answer;
    }
}