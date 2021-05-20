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

    public static void main(String[] args) throws IOException {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            System.out.println("Server connection established.");
            while (true) {
                String message = write();
                if (message.equals("repeat")){
                    continue;
                }
                String answer = read();
                // finding a phrase in a string
                int check = answer.indexOf("close client");
                if (message.equals("exit") || check != -1 ) {
                    System.out.println("Program will be finished now. See you again:)");
                    System.exit(0);
                    break;
                }
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
     * @throws IOException - read exception
     */
    public static String write() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a command: ");
        String command = scanner.nextLine();
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
                    Helper receiver = new Helper();
                    String data = receiver.returnDate();
                    SpaceMarine newSpaceMarine = new SpaceMarine(Integer.parseInt(userCommand[1]), receiver.scanName(),
                            receiver.scanCoordinates(), data, receiver.scanHealth(), receiver.scanCategory(),
                            receiver.scanWeapon(), receiver.scanMeleeWeapon(), receiver.scanChapter());
                    message.append(userCommand[0]).append(" ").append(newSpaceMarine.getId()).append(" ")
                            .append(newSpaceMarine.getName()).append(" ").
                            append(newSpaceMarine.getCoordinates().getXCord()).append(" ")
                            .append(newSpaceMarine.getCoordinates().getYCord()).append(" ").
                            append(newSpaceMarine.getCreationDate()).append(" ")
                            .append(newSpaceMarine.getHealth()).append(" ").append(newSpaceMarine.getCategory())
                            .append(" ").append(newSpaceMarine.getWeaponType()).append(" ")
                            .append(newSpaceMarine.getMeleeWeapon()).append(" ")
                            .append(newSpaceMarine.getChapter().getChapterName()).append(" ")
                            .append(newSpaceMarine.getChapter().getChapterWorld());
                    buffer = message.toString().getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                    socket.send(packet);
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("As an argument you need to enter a number.");
                    command = "repeat";
                }
            } else {
                System.out.println("This command needs an argument.");
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
                        Helper receiver = new Helper();
                        String newName = receiver.scanName();
                        StringBuilder message = new StringBuilder();
                        message.append(userCommand[0]).append(" ").append(userCommand[1].length())
                                .append(userCommand[1]).append(newName);
                        buffer = message.toString().getBytes();
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                        socket.send(packet);
                    } catch (NumberFormatException numberFormatException) {
                        System.out.println("As an argument you need to enter a number.");
                        command = "repeat";
                    }
                } else {
                    System.out.println("This command needs an argument.");
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
                            Helper receiver = new Helper();
                            Integer newHealth = receiver.scanHealth();
                            StringBuilder message = new StringBuilder();
                            message.append(userCommand[0]).append(" ").append(userCommand[1].length())
                                    .append(userCommand[1]).append(newHealth);
                            buffer = message.toString().getBytes();
                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, servicePort);
                            socket.send(packet);
                        } catch (NumberFormatException numberFormatException) {
                            System.out.println("As an argument you need to enter a number.");
                            command = "repeat";
                        }
                    } else {
                        System.out.println("This command needs an argument.");
                        command = "repeat";
                    }
                } else {
                    if (userCommand[0].equals("exit")) {
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
     * @throws IOException - receiving exception
     * @return
     */
    public static String read() throws IOException {
        byte[] bufferOut = new byte[65535];
        DatagramPacket fromServer = new DatagramPacket(bufferOut, bufferOut.length);
        socket.receive(fromServer);
        String answer = new String(fromServer.getData(), 0, fromServer.getLength());
        System.out.println("-----------------------------------------------------------------------------------\n"
                + answer + "\n-----------------------------------------------------------------------------------");
        return answer;
    }

    /**
     * Method to terminate a client
     */
    public static void exit() {
        System.out.println("Program will be finished now. See you again:)");
        System.exit(0);
    }
}

