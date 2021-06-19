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
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

public class DataBase {
    static final Logger logger = Logger.getLogger(DataBase.class.getName());
    private static String url = "jdbc:postgresql://localhost:7887/studs";
    private static Statement statement;
    private static Connection connection;
    private static String username = "s312439";
    private static String password = "gel443";
    private static SessionFactory factory;
    private static List<SpaceMarines> spaceMarines;

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
    public static void connect(TreeMap<Integer, SpaceMarine> collection) throws SQLException, ClassNotFoundException {
        factory = new Configuration().configure().buildSessionFactory();
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, username, password);
        logger.info("Database connection established.");
        statement = connection.createStatement();
        loadCollection(collection);
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
    public static void loadCollection(TreeMap<Integer, SpaceMarine> collection){
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
        UserService userService = new UserService();
        System.out.println(MD5(password));
        User user = new User(login, MD5(password));
        userService.saveUser(user);
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
}