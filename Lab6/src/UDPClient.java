import data.SpaceMarine;

import java.io.*;
import java.net.*;
import java.util.*;

public class UDPClient{

    /** Порт сервера, к которому собирается подключиться клиентский сокет */
    public final static int servicePort = 50001;
    private static DatagramSocket clientSocket; // socket for  для общения
    private static Socket socket;
    private static byte[] buffer;
    private static final Serialization serialization = new Serialization(); // сериализптор/десериализатор
    private static BufferedReader reader; // ридер читающий с консоли
    private static BufferedReader in;
    private static BufferedWriter out;
    private static InetAddress address;

        /**
         * Это main)
         *
         * @param args - что-то
         */
        public static void main(String[] args) throws IOException {
            try {
                connection(true);
                while (true) {
                    String message = write();
                    read();
                    if (message.equals("exit")) {
                        connection(false);
                        break;
                    }
                    read();
                }
            } catch (IOException ioException) {
                System.err.println("Server is not available at the moment. Try to reconnect?" +
                        "\nEnter 'yes' or 'no'.");
                String answer;
                while (!(answer = reader.readLine()).equals("yes")) {
                    switch (answer) {
                        case "":
                            break;
                        case "no":
                            exit();
                            break;
                        default:
                            System.out.println("Please write a correct answer.");
                    }
                }
                System.out.print("Connecting...");
            } catch (NoSuchElementException | ArrayIndexOutOfBoundsException exception) {
                System.out.println("Program will be finished now.");
                System.exit(0);
            }
        }

    /**
     * Server connection module
     *
     * @param connect - operating mode (disconnect / connect)
     * @throws IOException - exception of connection
     */
    public static void connection(boolean connect) throws IOException {
        if (connect) {
            clientSocket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
            System.out.println("Server connection established.");
        }
        if (!connect) {
            System.out.println("Client was closed");
            clientSocket.close();
        }
    }

        /**
         * Модуль отправки запросов на сервер
         *
         * @return - введённая команда
         * @throws IOException - ошибка чтения
         */
        public static String write() throws IOException {
            System.out.println("Enter a command: ");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String command = reader.readLine();
            String[] userCommand = command.trim().split(" ", 3);
            buffer = command.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4242);
            clientSocket.send(packet);
            out.write(command + "\n");
            out.flush();
            return command;
        }

        /**
         * Модуль принятия сообщений от сервера
         *
         * @throws IOException - ошибка принятия сообщений
         */
        public static void read() throws IOException {
            byte[] bufferOut = new byte[65535];
            DatagramPacket fromServer = new DatagramPacket(bufferOut, bufferOut.length);
            clientSocket.receive(fromServer);
            String answer = new String(fromServer.getData(), 0, fromServer.getLength());
            System.out.println(answer);
        }

        public static void exit(){
            System.out.println("Program will be finished now. See you again:)");
            System.exit(0);
        }
    }

