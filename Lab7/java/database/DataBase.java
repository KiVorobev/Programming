package database;

import data.Chapter;
import data.Coordinates;
import data.SpaceMarine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class DataBase {
    static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static String url = "jdbc:postgresql://localhost:7887/studs";
    private static Statement statement;
    private static Connection connection;
    private static String username = "s312439";
    private static String password = "gel443";
    private static SessionFactory factory;
    /** Field for saving date of initialization the collection */
    private static java.time.LocalDateTime initializationDate;
    /** HashMap collection for making a manual */
    private static HashMap<String, String> infoCommands;
    /** Set for storing paths to files for command 'execute_script' */
    private static Set<String> paths = new HashSet<>();
    {
        // Making a manual
        infoCommands = new HashMap<>();
        infoCommands.put("help", "                             - Display help for available commands");
        infoCommands.put("info", "                             - Output collection information to the standard\n" +
                "                                   output stream");
        infoCommands.put("show", "                             - Output all elements of the collection in a\n" +
                "                                   string representation to the standard output\n" +
                "                                   stream");
        infoCommands.put("insert number_of_key", "             - Add a new element with the specified key");
        infoCommands.put("update id", "                        - Update the values of a collection element whose\n" +
                "                                   id is equal to the specified one");
        infoCommands.put("remove_key number_of_key", "         - Delete an item from the collection by its key");
        infoCommands.put("clear", "                            - Clear the collection");
        infoCommands.put("execute_script file_name", "         - Read and execute the script from the specified\n" +
                "                                   file");
        infoCommands.put("exit", "                             - End the program");
        infoCommands.put("remove_greater value_of_health", "   - Remove all items from the collection that exceed\n" +
                "                                   the specified limit");
        infoCommands.put("replace_if_greater number_of_key", " - Replace the value by key if the new value is\n" +
                "                                   greater than the old one");
        infoCommands.put("remove_greater_key number_of_key", " - Remove all items from the collection whose key\n" +
                "                                   exceeds the specified value");
        infoCommands.put("group_counting_by_coordinates", "    - Group the collection items by the coordinates\n" +
                "                                   field value, output the number of items in each\n" +
                "                                   group");
        infoCommands.put("filter_by_chapter chapter_name", "   - Output elements whose chapter field value is\n" +
                "                                   equal to the specified value");
        infoCommands.put("filter_starts_with_name name", "     - Output elements whose name field value starts\n" +
                "                                   with the specified substring");
    }

    public DataBase(){}

    public DataBase(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Connecting with database
     *
     * @param collection collection
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public void connect(TreeMap<Integer, SpaceMarine> collection) throws SQLException, ClassNotFoundException {
            factory = new Configuration().configure().buildSessionFactory();
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            loadCollection(collection);
            initializationDate = java.time.LocalDateTime.now();
            logger.info("Database connection established.");
    }

    /**
     * Disconnecting with database
     */
    public static void disconnect(){
        try {
            statement.close();
            connection.close();
            logger.info("Database has been disconnected.");
        } catch (SQLException sqlException) {
            logger.severe("Something went wrong...");
        }
    }

    /**
     * Loading collection from DB to the server memory
     *
     * @param collection collection
     */
    public void loadCollection(TreeMap<Integer, SpaceMarine> collection){
        collection.clear();
        SpaceMarinesDao spaceMarinesDao = new SpaceMarinesDao();
        List<SpaceMarines> collect = new ArrayList<SpaceMarines>(spaceMarinesDao.findAll()) {
        };
        if (collect != null && !collect.isEmpty()) {
            for (SpaceMarines spaceMarine : collect) {
                Coordinates newCord = new Coordinates(spaceMarine.getxCord(), spaceMarine.getyCord());
                Chapter newChap = new Chapter(spaceMarine.getChapterName(), spaceMarine.getChapterWorld());
                SpaceMarine newSpaceMarine = new SpaceMarine(spaceMarine.getId(), spaceMarine.getName(), newCord,
                        spaceMarine.getCreationDate(), spaceMarine.getHealth(), spaceMarine.getCategory(),
                        spaceMarine.getWeaponType(), spaceMarine.getMeleeWeapon(), newChap, spaceMarine.getUser());
                collection.put(spaceMarine.getKey(), newSpaceMarine);
            }
        }
    }

    /**
     * Adding a new user
     *
     * @param login login
     * @param password password
     */
    public static String addUser(String login, String password) {
        logger.info("Trying to add a user to the database...");
        List<User> list = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createQuery("from User ");
            list = (List<User>) query.list();

            session.getTransaction().commit();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        if (list != null && !list.isEmpty()) {
            for (User user : list) {
                if (user.getLogin().equals(login)) {
                    return "User with this login is already exists. Choose another login, please.";
                }
            }
        }
        UserDao userDao = new UserDao();
        User user = new User(login, MD5(password));
        userDao.save(user);
        return "You have successfully registered.";
    }

    /**
     * authorisation of existing user
     *
     * @param login login
     * @param password password
     * @return answer to user
     */
    public static String login(String login, String password) {
        List<User> list = null;
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()){
            session.beginTransaction();

            Query query = session.createQuery("from User ");
            list = (List<User>) query.list();

            session.getTransaction().commit();
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        if (list != null && !list.isEmpty()) {
            for (User user : list) {
                if (user.getLogin().equals(login)) {
                    if (user.getPassword().equals(MD5(password))) {
                        return "You have successfully log in.";
                    } else return "Password is incorrect";
                }
            }
        }
           return "User with this login does not exist";
    }

    /**
     * Password hashing by the MD-5 algorithm
     *
     * @param password  password
     * @return hashed password
     */
    public static String MD5(String password){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md5.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public void setInitializationDate(LocalDateTime initializationDate) {
        this.initializationDate = initializationDate;
    }

    public static Set<String> getPaths() {
        return paths;
    }

    public static void setPaths(Set<String> paths) {
        DataBase.paths = paths;
    }

    public static HashMap<String, String> getInfoCommands() {
        return infoCommands;
    }

    public static void setInfoCommands(HashMap<String, String> infoCom) {
        infoCommands = infoCom;
    }
}