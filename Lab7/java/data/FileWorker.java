package data;

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.util.logging.Logger;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for parsing XML file and saving it
 */
public class FileWorker {

    /** TreeMap collection for keeping a collection as java-object */
    private TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
    /** Field for saving date of initialization the collection */
    private java.time.LocalDateTime initializationDate;
    /** HashMap collection for making a manual */
    private HashMap<String, String> infoCommands;
    /** Field used for saving collection into xml file */
    private File xmlFile;
    /** Field used for storing path to file with collection */
    private String pathToFile;
    /** Field used to determine the starting state of the file */
    private boolean needToClear = false;
    /** Set for storing paths to files for command 'execute_script' */
    private static Set<String> paths = new HashSet<>();
    /** Logger */
    private static final Logger logger = Logger.getLogger(FileWorker.class.getName());
    /** Field used to determine the starting state of the file */
    private boolean needToCreate = false;
    /** Field for checking a created file */
    private boolean needToRecheck = false;

    {
        initializationDate = java.time.LocalDateTime.now();

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

    /** Constructor for checking a path to file existence and file readiness to work */
    public FileWorker() throws IOException {
    }

    /**
     * Заполнение коллекции при первом запуске
     *
     * @param element - элемент из БД
     * @param collection - коллекция
     */
    public void fill(String element, TreeMap<Integer, SpaceMarine> collection) {
        String[] fields;
        int index;
        Integer key = 0;
        SpaceMarine spaceMarine = new SpaceMarine();
        fields = element.split(",");

        for (index = 0; index<fields.length; index++){
            if (fields[index].equals("null")){
                fields[index] = null;
            }
        }
        //spaceMarine.setId(fields[0]);
        spaceMarine.setName(fields[1]);
        //spaceMarine.setCoordinates(fields[2], fields[3]);
        //spaceMarine.setCreationDate(LocalDateTime.parse(fields[4]));
        //spaceMarine.setHealth(fields[5]);
        //spaceMarine.setCategory(fields[6]);
        //spaceMarine.setWeaponType(fields[7]);
        //spaceMarine.setMeleeWeapon(fields[8]);
        //spaceMarine.setChapter(fields[9], fields[10]);
        //spaceMarine.setUser(fields[14]);
        collection.put(key,spaceMarine);
    }


    public TreeMap<Integer, SpaceMarine> getSpaceMarines() {
        return spaceMarines;
    }

    public void setSpaceMarines(TreeMap<Integer, SpaceMarine> spaceMarines) {
        this.spaceMarines = spaceMarines;
    }

    public HashMap<String, String> getInfoCommands() {
        return infoCommands;
    }

    public void setInfoCommands(HashMap<String, String> infoCommands) {
        this.infoCommands = infoCommands;
    }

    public LocalDateTime getInitializationDate() {
        return initializationDate;
    }

    public void setInitializationDate(LocalDateTime initializationDate) {
        this.initializationDate = initializationDate;
    }

    public static Set<String> getPaths() {
        return paths;
    }

    public static void setPaths(Set<String> paths) {
        FileWorker.paths = paths;
    }

    public boolean isNeedToCreate() {
        return needToCreate;
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    public boolean isNeedToRecheck() {
        return needToRecheck;
    }

    public void setNeedToRecheck(boolean needToRecheck) {
        this.needToRecheck = needToRecheck;
    }
}