import data.SpaceMarine;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class for starting a client
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class UDPClient {

    /**
     * Server port to which the client socket is going to connect
     */
    public final static int servicePort = 2828;
    /**
     * Socket for communication
     */
    private static DatagramSocket socket;
    /**
     * Field for storing the command in bytes
     */
    private static byte[] buffer;
    /**
     * Field for storing the command in bytes
     */
    private static byte[] logBuffer;
    /**
     * Field for storing the IP-address
     */
    private static InetAddress address;
    /**
     * Field for storing the login of user
     */
    private static String clientLogin;
    /**
     * Field for storing the password of user
     */
    private static String clientPassword;

    /**
     * Client entry point
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            System.out.println("                              /  /           \\  \\\n" +
                    "                             /  /             \\  \\\n" +
                    "         Welcome to         /  /               \\  \\             By\n" +
                    "         Client App        /  /                 \\  \\       Kirill Vorobyev\n" +
                    "__________________________/  /                   \\  \\___________________________\n" +
                    "____________________________/                     \\_____________________________\n" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            address = InetAddress.getByName("localhost");
            socket = new DatagramSocket();
            authorisation();
            socket.close();
                while (true) {
                    socket = new DatagramSocket();
                    String message = write();
                    if (message.equals("exit")) {
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                                "Program will be finished now. See you again:)");
                        System.exit(0);
                        socket.close();
                        break;
                    }
                    if (message.equals("repeat")) {
                        continue;
                    }
                    read();
                    socket.close();
                }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("Program was stopped successfully.");
            System.exit(0);
        }
    }

    /**
     * Module for sending requests to the server
     *
     * @return String - entered command
     *
     * @throws IOException receiving exception
     * @throws InterruptedException exception of interrupted
     */
    public static String write() throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        String autoLog = "0 " + clientLogin + " " + clientPassword;
        while (command.equals("")) {
            System.out.print("Enter a command: ");
            command = scanner.nextLine();
            if (command.equals("")) {
                System.out.println("--------------------------------------------------------------------------------\n" +
                        "A command cannot be empty.\n" +
                        "--------------------------------------------------------------------------------");
            }
        }
        //Regular expression to match space(s)
        String regex = "\\s+";
        //Replacing the pattern with single space
        String testCommand = command.replaceAll(regex, " ");
        String[] userCommand = testCommand.trim().split(" ", 2);
        if (userCommand[0].equals("insert")) {
            if (userCommand.length == 2) {
                try {
                    int check;
                    if (userCommand[1].indexOf(" ") > 0) {
                        check = Integer.parseInt(userCommand[1].substring(0, userCommand[1].indexOf(" ")));
                    } else {
                        check = Integer.parseInt(userCommand[1]);
                    }
                    userCommand[1] = String.valueOf(check);
                    StringBuilder message = new StringBuilder();
                    InputChecker inputChecker = new InputChecker();
                    SpaceMarine newSpaceMarine = new SpaceMarine(Integer.parseInt(userCommand[1]), inputChecker.scanName(),
                            inputChecker.scanCoordinates(), inputChecker.returnDate(), inputChecker.scanHealth(), inputChecker.scanCategory(),
                            inputChecker.scanWeapon(), inputChecker.scanMeleeWeapon(), inputChecker.scanChapter(), clientLogin);
                    message.append(userCommand[0]).append(" \n").append(newSpaceMarine.getId()).append("\n")
                            .append(newSpaceMarine.getName()).append("\n").
                            append(newSpaceMarine.getCoordinates().getXCord()).append("\n")
                            .append(newSpaceMarine.getCoordinates().getYCord()).append("\n").
                            append(newSpaceMarine.getCreationDate()).append("\n")
                            .append(newSpaceMarine.getHealth()).append("\n").append(newSpaceMarine.getCategory())
                            .append("\n").append(newSpaceMarine.getWeaponType()).append("\n")
                            .append(newSpaceMarine.getMeleeWeapon()).append("\n")
                            .append(newSpaceMarine.getChapter().getChapterName()).append("\n")
                            .append(newSpaceMarine.getChapter().getChapterWorld()).append("\n").append(newSpaceMarine.getUser());
                    logBuffer = autoLog.getBytes();
                    DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                    socket.send(autoPacket);
                    TimeUnit.MILLISECONDS.sleep(100);
                    buffer = message.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                    socket.send(packet);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("--------------------------------------------------------------------------------\n" +
                            "Argument must be of type integer. Try again.\n" +
                            "--------------------------------------------------------------------------------");
                    command = "repeat";
                }
            } else {
                System.out.println("--------------------------------------------------------------------------------\n" +
                        "Please, enter number_of_key.\n" +
                        "--------------------------------------------------------------------------------");
                command = "repeat";
            }
        } else {
            if (userCommand[0].equals("update")) {
                if (userCommand.length == 2) {
                    try {
                        int check;
                        if (userCommand[1].indexOf(" ") > 0) {
                            check = Integer.parseInt(userCommand[1].substring(0, userCommand[1].indexOf(" ")));
                        } else {
                            check = Integer.parseInt(userCommand[1]);
                        }
                        userCommand[1] = String.valueOf(check);
                        InputChecker inputChecker = new InputChecker();
                        StringBuilder message = new StringBuilder();
                        SpaceMarine newSpaceMarine = new SpaceMarine(Integer.parseInt(userCommand[1]), inputChecker.scanName(),
                                inputChecker.scanCoordinates(), inputChecker.returnDate(), inputChecker.scanHealth(), inputChecker.scanCategory(),
                                inputChecker.scanWeapon(), inputChecker.scanMeleeWeapon(), inputChecker.scanChapter(), clientLogin);
                        message.append(userCommand[0]).append(" \n").append(newSpaceMarine.getId()).append("\n")
                                .append(newSpaceMarine.getName()).append("\n").
                                append(newSpaceMarine.getCoordinates().getXCord()).append("\n")
                                .append(newSpaceMarine.getCoordinates().getYCord()).append("\n").
                                append(newSpaceMarine.getCreationDate()).append("\n")
                                .append(newSpaceMarine.getHealth()).append("\n").append(newSpaceMarine.getCategory())
                                .append("\n").append(newSpaceMarine.getWeaponType()).append("\n")
                                .append(newSpaceMarine.getMeleeWeapon()).append("\n")
                                .append(newSpaceMarine.getChapter().getChapterName()).append("\n")
                                .append(newSpaceMarine.getChapter().getChapterWorld()).append("\n").append(newSpaceMarine.getUser());
                        logBuffer = autoLog.getBytes();
                        DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                        socket.send(autoPacket);
                        TimeUnit.MILLISECONDS.sleep(100);
                        buffer = message.toString().getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                        socket.send(packet);
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("--------------------------------------------------------------------------------\n" +
                                "Argument must be of type integer. Try again.\n" +
                                "--------------------------------------------------------------------------------");
                        command = "repeat";
                    }
                } else {
                    System.out.println("--------------------------------------------------------------------------------\n" +
                            "Please, enter id.\n" +
                            "--------------------------------------------------------------------------------");
                    command = "repeat";
                }
            } else {
                if (userCommand[0].equals("replace_if_greater")) {
                    if (userCommand.length == 2) {
                        try {
                            int check;
                            if (userCommand[1].indexOf(" ") > 0) {
                                check = Integer.parseInt(userCommand[1].substring(0, userCommand[1].indexOf(" ")));
                            } else {
                                check = Integer.parseInt(userCommand[1]);
                            }
                            userCommand[1] = String.valueOf(check);
                            InputChecker receiver = new InputChecker();
                            Integer newHealth = receiver.scanHealth();
                            StringBuilder message = new StringBuilder();
                            message.append(userCommand[0]).append(" \n").append(userCommand[1])
                                    .append("\n").append(newHealth);
                            logBuffer = autoLog.getBytes();
                            DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                            socket.send(autoPacket);
                            TimeUnit.MILLISECONDS.sleep(100);
                            buffer = message.toString().getBytes();
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                            socket.send(packet);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("--------------------------------------------------------------------------------\n" +
                                    "Argument must be of type integer. Try again.\n" +
                                    "--------------------------------------------------------------------------------\n");
                            command = "repeat";
                        }
                    } else {
                        System.out.println("--------------------------------------------------------------------------------\n" +
                                "Please, enter number_of_key.\n" +
                                "--------------------------------------------------------------------------------");
                        command = "repeat";
                    }
                } else {
                    if (userCommand[0].equals("exit")) {
                        logBuffer = autoLog.getBytes();
                        DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                        socket.send(autoPacket);
                        TimeUnit.MILLISECONDS.sleep(100);
                        buffer = userCommand[0].getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                        socket.send(packet);
                        command = "exit";
                    } else {
                        if (userCommand[0].equals("clear") || userCommand[0].equals("group_counting_by_coordinates") ||
                                userCommand[0].equals("show") || userCommand[0].equals("info") || userCommand[0].equals("help")) {
                            logBuffer = autoLog.getBytes();
                            DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                            socket.send(autoPacket);
                            TimeUnit.MILLISECONDS.sleep(100);
                            buffer = userCommand[0].getBytes();
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                            socket.send(packet);
                        } else {
                            if (userCommand[0].equals("filter_by_chapter")) {
                                if (userCommand.length == 2) {
                                    StringBuilder message = new StringBuilder();
                                    message.append(userCommand[0]).append(" ").append(userCommand[1]);
                                    logBuffer = autoLog.getBytes();
                                    DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                                    socket.send(autoPacket);
                                    TimeUnit.MILLISECONDS.sleep(100);
                                    buffer = message.toString().getBytes();
                                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                                    socket.send(packet);
                                } else {
                                    System.out.println("--------------------------------------------------------------------------------\n" +
                                            "Please, enter chapter_name.\n" +
                                            "--------------------------------------------------------------------------------");
                                    command = "repeat";
                                }
                            } else {
                                if (userCommand[0].equals("filter_starts_with_name")) {
                                    if (userCommand.length == 2) {
                                        StringBuilder message = new StringBuilder();
                                        message.append(userCommand[0]).append(" ").append(userCommand[1]);
                                        logBuffer = autoLog.getBytes();
                                        DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                                        socket.send(autoPacket);
                                        buffer = message.toString().getBytes();
                                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                                        socket.send(packet);
                                    } else {
                                        System.out.println("--------------------------------------------------------------------------------\n" +
                                                "Please, enter a name.\n" +
                                                "--------------------------------------------------------------------------------");
                                        command = "repeat";
                                    }
                                } else {
                                    if (userCommand[0].equals("remove_greater")) {
                                        if (userCommand.length == 2) {
                                            StringBuilder message = new StringBuilder();
                                            try {
                                                String test = userCommand[1];
                                                while (test.substring(0, 1).equals(" ")) {
                                                    test = test.substring(1, test.length());
                                                }
                                                int health;
                                                if (test.indexOf(" ") > 0) {
                                                    health = Integer.parseInt(test.substring(0, test.indexOf(" ")));
                                                } else {
                                                    health = Integer.parseInt(test);
                                                }
                                                message.append(userCommand[0]).append(" ").append(health);
                                                logBuffer = autoLog.getBytes();
                                                DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                                                socket.send(autoPacket);
                                                buffer = message.toString().getBytes();
                                                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                                                socket.send(packet);
                                            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                                                System.out.println("--------------------------------------------------------------------------------\n" +
                                                        "Argument must be of type integer. Try again.\n" +
                                                        "--------------------------------------------------------------------------------");
                                                command = "repeat";
                                            }
                                        } else {
                                            System.out.println("--------------------------------------------------------------------------------\n" +
                                                    "Please, enter value_of_health.\n" +
                                                    "--------------------------------------------------------------------------------");
                                            command = "repeat";
                                        }
                                    } else {
                                        if (userCommand[0].equals("remove_greater_key") || userCommand[0].equals("remove_key")) {
                                            if (userCommand.length == 2) {
                                                StringBuilder message = new StringBuilder();
                                                try {
                                                    String test = userCommand[1];
                                                    while (test.substring(0, 1).equals(" ")) {
                                                        test = test.substring(1, test.length());
                                                    }
                                                    int key;
                                                    if (test.indexOf(" ") > 0) {
                                                        key = Integer.parseInt(test.substring(0, test.indexOf(" ")));
                                                    } else {
                                                        key = Integer.parseInt(test);
                                                    }
                                                    message.append(userCommand[0]).append(" ").append(key);
                                                    logBuffer = autoLog.getBytes();
                                                    DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                                                    socket.send(autoPacket);
                                                    buffer = message.toString().getBytes();
                                                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                                                    socket.send(packet);
                                                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                                                    System.out.println("--------------------------------------------------------------------------------\n" +
                                                            "Argument must be of type integer. Try again.\n" +
                                                            "--------------------------------------------------------------------------------");
                                                    command = "repeat";
                                                }
                                            } else {
                                                System.out.println("--------------------------------------------------------------------------------\n" +
                                                        "Please, enter number_of_key.\n" +
                                                        "--------------------------------------------------------------------------------");
                                                command = "repeat";
                                            }
                                        } else {
                                            if (userCommand[0].equals("execute_script")) {
                                                if (userCommand.length == 2){
                                                    StringBuilder message = new StringBuilder();
                                                    message.append(userCommand[0]).append(" ").append(userCommand[1]);
                                                    logBuffer = autoLog.getBytes();
                                                    DatagramPacket autoPacket = new DatagramPacket(logBuffer, logBuffer.length, address, servicePort);
                                                    socket.send(autoPacket);
                                                    buffer = message.toString().getBytes();
                                                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                                                    socket.send(packet);
                                                } else {
                                                    System.out.println("--------------------------------------------------------------------------------\n" +
                                                            "Please, enter file_name.\n" +
                                                            "--------------------------------------------------------------------------------");
                                                    command = "repeat";
                                                }
                                            } else {
                                                System.out.println("--------------------------------------------------------------------------------\n" +
                                                                "Unknown command. Write 'help' for reference.\n" +
                                                        "--------------------------------------------------------------------------------");
                                                command = "repeat";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return command;
    }

    /**
     * Module for receiving messages from the server
     *
     * @return String - answer from server
     * @throws IOException - receiving exception
     */
    public static String read() throws IOException {
        try {
            byte[] bufferOut = new byte[65535];
            DatagramPacket fromServer = new DatagramPacket(bufferOut, bufferOut.length);
            socket.setSoTimeout(7000);
            socket.receive(fromServer);
            String answer = new String(fromServer.getData(), 0, fromServer.getLength());
            System.out.println("--------------------------------------------------------------------------------\n"
                    + answer + "\n--------------------------------------------------------------------------------");
            return answer;
        } catch (SocketTimeoutException socketTimeoutException) {
            System.out.println("Server is unavailable. Try later.");
            return "Server is unavailable. Try later.";
        }
    }

    /**
     * Module for authorisation user
     */
    public static void authorisation() {
        System.out.println("You need to log in for connect to the database.");
        while (true){
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("1 - Sign in || 2 - Sign up || 3 - Exit");
                System.out.print("Enter a command: ");
                String message = scanner.nextLine();
                String login = "";
                String password = "";
                boolean needToRelog = true;
                boolean needToRepas = true;
                StringBuilder credentials = new StringBuilder();
                if (message.equals("3") || message.equals("Exit")){
                    System.out.println("Program will be finished now. See you again:)");
                    System.exit(0);
                } else
                if (message.equals("1") || message.equals("Sign in") || message.equals("2") || message.equals("Sign up")) {
                    if (message.equals("1") || message.equals("Sign up")) {
                        credentials.append("1").append(" ");
                    } else credentials.append("2").append(" ");
                    while (needToRelog) {
                        needToRelog = false;
                        System.out.print("Enter login(1-255 characters): ");
                        login = scanner.nextLine().toLowerCase();
                        if (login.equals("")) {
                            System.out.println("--------------------------------------------------------------------------------\n" +
                                    "Login cannot be empty." +
                                    "\n--------------------------------------------------------------------------------");
                            needToRelog = true;
                        } else {
                            if (login.indexOf(" ") >= 0) {
                                System.out.println("--------------------------------------------------------------------------------\n" +
                                        "Login cannot contain spaces." +
                                        "\n--------------------------------------------------------------------------------");
                                needToRelog = true;
                            } else {
                                if (login.length() < 1 || login.length() > 255) {
                                    System.out.println("--------------------------------------------------------------------------------\n" +
                                            "Login can contain 1-255 characters." +
                                            "\n--------------------------------------------------------------------------------");
                                    needToRelog = true;
                                }
                            }
                        }
                    }
                    while (needToRepas) {
                        needToRepas = false;
                        System.out.print("Enter password(8-16 characters): ");
                        password = scanner.nextLine();
                        if (password.equals("")) {
                            System.out.println("--------------------------------------------------------------------------------\n" +
                                    "Password cannot be empty." +
                                    "\n--------------------------------------------------------------------------------");
                            needToRepas = true;
                        } else {
                            if (password.indexOf(" ") >= 0) {
                                System.out.println("--------------------------------------------------------------------------------\n" +
                                        "Password cannot contain spaces." +
                                        "\n--------------------------------------------------------------------------------");
                                needToRepas = true;
                            } else {
                                if (password.length() < 8 || password.length() > 16) {
                                    System.out.println("--------------------------------------------------------------------------------\n" +
                                            "Password can contain 8-16 characters." +
                                            "\n--------------------------------------------------------------------------------");
                                    needToRepas = true;
                                }
                            }
                        }
                    }
                    credentials.append(login).append(" ").append(password);
                    buffer = credentials.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                    socket.send(packet);
                    String messageFromServer = read();
                    if (messageFromServer.equals("You have successfully log in.") ||
                            messageFromServer.equals("You have successfully registered.")) {
                        clientLogin = login;
                        clientPassword = password;
                        break;
                    }
                } else {
                    System.out.println("--------------------------------------------------------------------------------\n" +
                            "Unknown command." +
                            "\n--------------------------------------------------------------------------------");
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("--------------------------------------------------------------------------------\n" +
                        "Authorisation error. Try again." +
                        "\n--------------------------------------------------------------------------------");
            }
        }
    }
}

