import data.SpaceMarine;

import java.io.*;
import java.net.*;
import java.util.*;

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
     * Field for storing the IP-address
     */
    private static InetAddress address;
    /**
     * Field for switch off the client
     */
    private static boolean needToOff = false;

    /**
     * Client entry point
     */
    public static void main(String[] args) throws IOException {
        try {
            System.out.println("                              /  /           \\  \\\n" +
                    "                             /  /             \\  \\\n" +
                    "         Welcome to         /  /               \\  \\             By\n" +
                    "         Client App        /  /                 \\  \\       Kirill Vorobyev\n" +
                    "__________________________/  /                   \\  \\___________________________\n" +
                    "____________________________/                     \\_____________________________\n" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            address = InetAddress.getByName("localhost");
            while (true) {
                socket = new DatagramSocket();
                String message = write();
                if (needToOff){
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
     * @throws IOException receiving exception
     */
    public static String write() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (command.equals("")){
            System.out.print("Enter a command: ");
            command = scanner.nextLine();
            if (command.equals("")){
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
                    String data = inputChecker.returnDate();
                    SpaceMarine newSpaceMarine = new SpaceMarine(Integer.parseInt(userCommand[1]), inputChecker.scanName(),
                            inputChecker.scanCoordinates(), data, inputChecker.scanHealth(), inputChecker.scanCategory(),
                            inputChecker.scanWeapon(), inputChecker.scanMeleeWeapon(), inputChecker.scanChapter());
                    message.append(userCommand[0]).append(" \n").append(newSpaceMarine.getId()).append("\n")
                            .append(newSpaceMarine.getName()).append("\n").
                            append(newSpaceMarine.getCoordinates().getXCord()).append("\n")
                            .append(newSpaceMarine.getCoordinates().getYCord()).append("\n").
                            append(newSpaceMarine.getCreationDate()).append("\n")
                            .append(newSpaceMarine.getHealth()).append("\n").append(newSpaceMarine.getCategory())
                            .append("\n").append(newSpaceMarine.getWeaponType()).append("\n")
                            .append(newSpaceMarine.getMeleeWeapon()).append("\n")
                            .append(newSpaceMarine.getChapter().getChapterName()).append("\n")
                            .append(newSpaceMarine.getChapter().getChapterWorld());
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
                        String data = inputChecker.returnDate();
                        SpaceMarine newSpaceMarine = new SpaceMarine(Integer.parseInt(userCommand[1]), inputChecker.scanName(),
                                inputChecker.scanCoordinates(), data, inputChecker.scanHealth(), inputChecker.scanCategory(),
                                inputChecker.scanWeapon(), inputChecker.scanMeleeWeapon(), inputChecker.scanChapter());
                        message.append(userCommand[0]).append(" \n").append(newSpaceMarine.getId()).append("\n")
                                .append(newSpaceMarine.getName()).append("\n").
                                append(newSpaceMarine.getCoordinates().getXCord()).append("\n")
                                .append(newSpaceMarine.getCoordinates().getYCord()).append("\n").
                                append(newSpaceMarine.getCreationDate()).append("\n")
                                .append(newSpaceMarine.getHealth()).append("\n").append(newSpaceMarine.getCategory())
                                .append("\n").append(newSpaceMarine.getWeaponType()).append("\n")
                                .append(newSpaceMarine.getMeleeWeapon()).append("\n")
                                .append(newSpaceMarine.getChapter().getChapterName()).append("\n")
                                .append(newSpaceMarine.getChapter().getChapterWorld());
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
                        needToOff = true;
                        buffer = command.getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                        socket.send(packet);
                        command = "exit";
                    } else {
                        buffer = command.getBytes();
                        DatagramPacket toServerPacket = new DatagramPacket(buffer, buffer.length, address, servicePort);
                        socket.send(toServerPacket);
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
            socket.setSoTimeout(3000);
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
}

