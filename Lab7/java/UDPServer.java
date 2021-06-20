import commands.*;
import data.SpaceMarine;
import database.DataBase;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.logging.*;

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
    private static byte[] buffer = new byte[65535];
    /**
     * Array of bytes for organizing a packet for sending answer to client
     */
    private static byte[] secondBuffer = new byte[65535];
    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger("");
    /**
     * TreeMap collection for keeping a collection as java-object
     */
    private static TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
    /**
     * User login
     */
    private static String clientLogin;
    /**
     * Field for check the user's authorisation status
     */
    private static boolean isAuthorisated;

    /**
     * Server entry point
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            DataBase dataBase = new DataBase();
            settingOfLogging();
            socket = new DatagramSocket(servicePort);
            dataBase.connect(spaceMarines);
            logger.info("Server started.");
            socket.close();
            while (true) {
                isAuthorisated = false;
                socket = new DatagramSocket(servicePort);
                authorisation();
                if (isAuthorisated) {
                    String message = read();
                    if (message != null) {
                        String forLoad = write();
                        if (forLoad.equals("Element added successfully.") || forLoad.equals("Your collection successfully cleared.")
                        || forLoad.contains("removed successfully.") || forLoad.equals("Health value updated successfully.")
                        || forLoad.equals("Element updated successfully.")){
                            dataBase.loadCollection(spaceMarines);
                        }
                    }
                    if (message.equals("exit")) clientLogin = null;
                }
                socket.close();
            }
        } catch(NoSuchElementException noSuchElementException){
            logger.severe("Program was stopped successfully.");
            System.exit(0);
        } catch(BindException bindException){
            logger.severe("Server is already running!");
        } catch (SQLException SQLException) {
            logger.severe("Failed to connect to database. Shutdown.");
        } catch (ClassNotFoundException classNotFoundException) {
            logger.severe("Class not found.");
        } catch (PersistenceException persistenceException) {
            logger.severe("Failed to connect to database. Shutdown.");
        }
    }

    /**
     * Module of authorisation user
     *
     * @throws InterruptedException
     */
    public static void authorisation() throws InterruptedException {
        while (true) {
            try {
                String answer;
                boolean needToBreak = false;
                String[] fields = read().split(" ");
                if (fields.length == 3) {
                    clientLogin = fields[1];
                    if (fields[0].equals("0")) {
                        needToBreak = true;
                        isAuthorisated = true;
                    } else {
                        if (fields[0].equals("2")) {
                            answer = DataBase.addUser(fields[1], fields[2]);
                            if (answer.equals("You have successfully registered."))
                                needToBreak = true;
                        } else if (fields[0].equals("1")) {
                            answer = DataBase.login(fields[1], fields[2]);
                            if (answer.equals("You have successfully log in."))
                                needToBreak = true;
                        } else answer = "Unknown command";
                        secondBuffer = answer.getBytes();
                        DatagramPacket toClientPacket = new DatagramPacket(secondBuffer, secondBuffer.length, address, port);
                        socket.send(toClientPacket);
                    }
                    if (needToBreak) break;
                }
            } catch (IOException e) {
                logger.severe("Authorisation error.");
            }
        }
    }

    /**
     * Module for connecting the server to a file with a collection
     *
     * @throws IOException - receiving exception
     */
    public static void settingOfLogging() throws IOException {
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
                            record.getLevel() + ": " + record.getMessage() + "\n";
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
        logger.info("User " + clientLogin + " entered: " + message);
        if (message.substring(0,1).equals("0") || message.substring(0,1).equals("1") || message.substring(0,1).equals("2")){
            userCommand = message.trim().split(" ", 3);
        } else {
            userCommand = message.trim().split(" ", 2);
        }
        return message;
    }

    /**
     * Module for sending answers to the client
     *
     * @throws IOException          - receiving exception
     * @throws InterruptedException - wait exception
     */
    public static String write() throws IOException, InterruptedException {
            String answer = " ";
            try {
                if (userCommand.length == 3) {
                    answer = " ";
                }
                if (userCommand.length == 1) {
                    answer = easyExecution(userCommand[0]);
                } else if (userCommand.length == 2) {
                    answer = hardExecution(userCommand[0], userCommand[1]);
                }
                    secondBuffer = answer.getBytes();
                    DatagramPacket toClientPacket = new DatagramPacket(secondBuffer, secondBuffer.length, address, port);
                    socket.send(toClientPacket);
            } catch (IOException ignored) {
                logger.severe("Sending answer error.");
            }
            return answer;
    }

    /**
     * Module for execution of commands without argument
     *
     * @return - String description of command
     */
    public static String easyExecution(String firstArg) {
        String answer;
        switch (firstArg) {
            case "help":
                answer = new Help().action(new DataBase());
                break;
            case "info":
                answer = new Info().action(spaceMarines, clientLogin);
                break;
            case "show":
                answer = new Show().action(spaceMarines);
                break;
            case "exit":
                answer = new Exit().action(spaceMarines);
                break;
            case "clear":
                answer = new Clear().action(spaceMarines, clientLogin);
                break;
            case "group_counting_by_coordinates":
                answer = new GroupCountingByCoordinates().action(spaceMarines, clientLogin);
                break;
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
    public static String hardExecution(String firstArg, String secondArg) {
        String answer;
        switch (firstArg) {
            case "insert":
                answer = new Insert().action(secondArg, spaceMarines);
                break;
            case "update":
                answer = new Update().action(secondArg, spaceMarines, clientLogin);
                break;
            case "remove_key":
                answer = new RemoveKey().action(secondArg, spaceMarines, clientLogin);
                break;
            case "execute_script":
                DataBase.getPaths().add(secondArg.toLowerCase());
                answer = new ExecuteScript().action(secondArg, spaceMarines, clientLogin);
                DataBase.getPaths().clear();
                break;
            case "remove_greater":
                answer = new RemoveGreater().action(secondArg, spaceMarines, clientLogin);
                break;
            case "replace_if_greater":
                answer = new ReplaceIfGreater().action(secondArg, spaceMarines, clientLogin);
                break;
            case "remove_greater_key":
                answer = new RemoveGreaterKey().action(secondArg, spaceMarines, clientLogin);
                break;
            case "filter_by_chapter":
                answer = new FilterByChapter().action(secondArg, spaceMarines, clientLogin);
                break;
            case "filter_starts_with_name":
                answer = new FilterStartsWithName().action(secondArg, spaceMarines, clientLogin);
                break;
            default:
                answer = "Unknown command. Write 'help' for reference.";
                break;
        }
        return answer;
    }
    public TreeMap<Integer, SpaceMarine> getSpaceMarines() {
        return spaceMarines;
    }

    public void setSpaceMarines(TreeMap<Integer, SpaceMarine> spaceMarines) {
        this.spaceMarines = spaceMarines;
    }
}