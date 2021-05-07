import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.time.LocalDateTime;
import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import data.*;

/**
 * @author Kirill Vorobyev
 * @version 1.2
 * Class which realised user`s commands
 */
public class CollectionManagement {

    /** TreeMap collection for keeping a collection as java-object */
    private TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
    /** Field for saving date of initialization thw collection */
    private java.time.LocalDateTime initializationDate;
    /** HashMap collection for making a manual */
    private HashMap<String, String> infoCommands;
    /** Field used for saving collection into xml file */
    private File xmlFile;
    /** Field used to determine the starting state of the file */
    boolean needToClear = false;

    {
        initializationDate = java.time.LocalDateTime.now();

        // Making a manual
        infoCommands = new HashMap<>();
        infoCommands.put("help", " - Display help for available commands");
        infoCommands.put("info", " - Output collection information to the standard output stream");
        infoCommands.put("show", " - Output all elements of the collection in a string representation to the standard output stream");
        infoCommands.put("insert number_of_key", " - Add a new element with the specified key");
        infoCommands.put("update id", " - Update the value of a collection element whose id is equal to the specified one");
        infoCommands.put("remove_key number_of_key", " - Delete an item from the collection by its key");
        infoCommands.put("clear", " - Clear the collection");
        infoCommands.put("save", " - Save the collection to a file");
        infoCommands.put("execute_script file_name", " - Read and execute the script from the specified file");
        infoCommands.put("exit", " - End the program");
        infoCommands.put("remove_greater value_of_health", " - Remove all items from the collection that exceed the specified limit");
        infoCommands.put("replace_if_greater value_of_health", " - Replace the value by key if the new value is greater than the old one");
        infoCommands.put("remove_greater_key number_of_key", " - Remove all items from the collection whose key exceeds the specified value");
        infoCommands.put("group_counting_by_coordinates", " - Group the collection items by the coordinates field value, output the number of items in each group");
        infoCommands.put("filter_by_chapter chapter_name", " - Output elements whose chapter field value is equal to the specified value");
        infoCommands.put("filter_starts_with_name name", " - Output elements whose name field value starts with the specified substring");
    }

    /** Constructor for checking a path to file existence and file readiness to work */
    public CollectionManagement(String pathToXML) throws IOException {
        try {
            if (checkFile(pathToXML)) {
                try {
                    final QName qName = new QName("spaceMarines");
                    InputStream inputStream = new FileInputStream(pathToXML);
                    xmlFile = new File(pathToXML);
                    // Create xml event reader for input stream
                    XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                    XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
                    // Initialize jaxb
                    JAXBContext context = JAXBContext.newInstance(SpaceMarines.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    XMLEvent e;
                    // Field for counting amount of downloaded elements
                    int goodElements = 0;
                    int badElements = 0;
                    // Loop for unmarshalling the collection
                    while ((e = xmlEventReader.peek()) != null) {
                        // Check the event is a Document start element
                        if (e.isStartElement() && ((StartElement) e).getName().equals(qName)) {
                            // Unmarshall the document
                            SpaceMarines unmarshalledSpaceMarine = unmarshaller.unmarshal(xmlEventReader, SpaceMarines.class).getValue();
                            boolean needToCheck;
                            for (Map.Entry<Integer, SpaceMarine> entry : unmarshalledSpaceMarine.getSpaceMarines().entrySet()) {
                                needToCheck = true;
                                for (SpaceMarine spaceMarine : spaceMarines.values()) {
                                    if (spaceMarine.getId() == entry.getValue().getId()) {
                                        badElements += 1;
                                        needToCheck = false;
                                    }
                                }
                                if (needToCheck) {
                                    if (!spaceMarines.containsKey(entry.getKey()) &&
                                            entry.getValue().getId() > 0 &&
                                            !spaceMarines.containsValue(entry.getValue().getId()) &&
                                            !entry.getValue().getName().equals(null) &&
                                            !entry.getValue().getCoordinates().getYCord().equals(null) &&
                                            !entry.getValue().getCreationDate().equals(null) &&
                                            (entry.getValue().getHealth() > 0 || entry.getValue().getHealth().equals(null)) &&
                                            !entry.getValue().getChapter().getChapterName().equals(null) &&
                                            !entry.getValue().getChapter().getChapterWorld().equals(null)) {
                                        spaceMarines.put(entry.getKey(), entry.getValue());
                                        goodElements += 1;
                                    } else badElements += 1;
                                }
                            }
                        } else {
                            xmlEventReader.next();
                        }
                    }
                    System.out.println("Collection was loaded successfully. " +
                            "\n" + goodElements + " elements has been loaded." +
                            "\n" + badElements + " elements were not loaded because these values are incorrect.");
                    xmlFile = new File(pathToXML);
                    initializationDate = LocalDateTime.now();
                } catch (JAXBException jaxbException) {
                    System.out.println("Violated XML syntax. Check the file and Try again.");
                    System.exit(0);
                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("File not found.");
                    System.exit(0);
                } catch (XMLStreamException xmlStreamException) {
                    xmlFile = new File (pathToXML);
                    needToClear = true;
                    System.out.println("Collection was loaded successfully.");
                    System.out.println("Collection is empty.");
                }
            } else {
                System.out.println("Try again.");
                System.exit(0);
            }
        } catch (NoSuchElementException noSuchElementException) {
            System.out.println("You clicked shortcut for finishing a program.");
            System.exit(0);
        }
    }

    /**
     * Class which check file is existed, and can be readable and writeable.
     * @return readiness status
     */
    public boolean checkFile(String pathToXML) {
        File checkingFile = new File(pathToXML);
        if (!checkingFile.exists()) {
            System.out.println("File not found. Try again.");
            return false;
        }
        if (!checkingFile.canRead()) {
            System.out.println("File cannot be readable. You should to have this permission.");
            return false;
        }
        if (!checkingFile.canWrite()) {
            System.out.println("File cannot be writeable. You should to have this permission.");
            return false;
        }
        return true;
    }

    /** Method for printing manual for user */
    public void help() {
        for (Map.Entry<String, String> entry : infoCommands.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }

    /** Method for printing information about the collection */
    public void info() {
        System.out.println("Collection Type: " + spaceMarines.getClass() +
                "\nInitialization date: " + initializationDate +
                "\nAmount of elements: " + spaceMarines.size());
    }

    /** Method for printing collection elements into the string representation */
    public void show() {
        if (!spaceMarines.isEmpty()) {
            for (Map.Entry<Integer, SpaceMarine> entry : spaceMarines.entrySet()) {
                System.out.print(entry.getKey() + " ");
                System.out.print(entry.getValue());
            }
        } else {
            System.out.println("Collection is empty");
        }
    }

    /** Method for adding element in the collection */
    public void insert(String in) {
        boolean check = false;
        try {
            int key;
            if (in.indexOf(" ") > 0) {
                key = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            } else {
                key = Integer.parseInt(in);
            }
            for (int keys : spaceMarines.keySet()) {
                if (key == keys) {
                    System.out.println("Such key already exists. Try again.");
                    check = true;
                }
            }
            if (!check) {
                SpaceMarine newSpaceMarine = new SpaceMarine(makeId(), scanName(), scanCoordinates(), returnDate(), scanHealth(), scanCategory(), scanWeapon(), scanMeleeWeapon(), scanChapter());
                spaceMarines.put(key, newSpaceMarine);
                System.out.println("Element added successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("As an argument you need to enter a number.");
        }
    }

    /**
     * Method for receiving ID of element
     * @return int ID
     */
    public int makeId() {
        int maxId = 0;
        for (Map.Entry<Integer, SpaceMarine> marines : spaceMarines.entrySet()) {
            if (marines.getValue().getId() > maxId) {
                maxId = marines.getValue().getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Method for receiving name of element
     * @return String name
     */
    public String scanName() {
        for (; ; ) {
            try {
                // System.out.println("Attention! If name will be so long, you may lose some part of this information");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter a name: ");
                String name = scanner.nextLine().trim();
                if (name.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return name;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving x-coordinate of element
     * @return int x
     */
    public int scanX() {
        for (; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter X coordinate: ");
                int x = scanner.nextInt();
                return x;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving y-coordinate of element
     * @return Integer y
     */
    public Integer scanY() {
        for (; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter Y coordinate. Max value is 941: ");
                Integer y = scanner.nextInt();
                if (y.equals("")) {
                    System.out.println("This value cannot be empty. Try again.");
                    continue;
                }
                if (y > 941){
                    System.out.println("Max value is 941. Try again.");
                    continue;
                }
                return y;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /** Method for making coordinates by using methods scanX() and scanY() */
    public Coordinates scanCoordinates() {
        return new Coordinates(scanX(), scanY());
    }

    /**
     * Method for receiving health of element
     * @return int health
     */
    public int scanHealth() {
        for (; ; ) {
            try {
                System.out.print("Enter health. Value must be greater than 0.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter health: ");
                int health = scanner.nextInt();
                if (health <= 0) {
                    System.out.println("This value must be greater than 0. Try again");
                    continue;
                }
                return health;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving category of element
     * @return AstartesCategory category
     */
    public AstartesCategory scanCategory() {
        for ( ; ; ) {
            try {
                //System.out.println("Choose variant of astartes category. Enter the number corresponding to the desired option. ");
                System.out.println("Variants: ASSAULT, TACTICAL, CHAPLAIN.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String category = scanner.nextLine().toUpperCase();
                switch (category) {
                    case "ASSAULT":
                        return AstartesCategory.ASSAULT;
                    case "TACTICAL":
                        return AstartesCategory.TACTICAL;
                    case "CHAPLAIN":
                        return AstartesCategory.CHAPLAIN;
                    default:
                        break;
                }
                System.out.println("You should to enter ASSAULT, TACTICAL, CHAPLAIN. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a words (ASSAULT, TACTICAL, CHAPLAIN). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving weapon of element
     * @return Weapon weapon
     */
    public Weapon scanWeapon(){
        for ( ; ; ) {
            try {
                //System.out.println("Choose variant of astartes category. Enter the number corresponding to the desired option. ");
                System.out.println("Variants: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String weapon = scanner.nextLine().toUpperCase();
                switch (weapon) {
                    case "BOLTGUN":
                        return Weapon.BOLTGUN;
                    case "PLASMA_GUN":
                        return Weapon.PLASMA_GUN;
                    case "COMBI_PLASMA_GUN":
                        return Weapon.COMBI_PLASMA_GUN;
                    case "GRENADE_LAUNCHER":
                        return Weapon.GRENADE_LAUNCHER;
                    default:
                        break;
                }
                System.out.println("You should to enter BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a words (BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving melee weapon of element
     * @return MeleeWeapon meleeWeapon
     */
    public MeleeWeapon scanMeleeWeapon(){
        for ( ; ; ) {
            try {
                //System.out.println("Choose variant of astartes category. Enter the number corresponding to the desired option. ");
                System.out.println("Variants: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String meleeWeapon = scanner.nextLine().toUpperCase();
                switch (meleeWeapon) {
                    case "CHAIN_SWORD":
                        return MeleeWeapon.CHAIN_SWORD;
                    case "CHAIN_AXE":
                        return MeleeWeapon.CHAIN_AXE;
                    case "POWER_BLADE":
                        return MeleeWeapon.POWER_BLADE;
                    case "POWER_FIST":
                        return MeleeWeapon.POWER_FIST;
                    default:
                        break;
                }
                System.out.println("You should to enter CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Try again. ");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a words (CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST). Try again. ");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving chapter name of element
     * @return String chapterName
     */
    public String scanChapterName(){
        for (; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter chapter name: ");
                String chapterName = scanner.nextLine();
                if (chapterName.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return chapterName;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /**
     * Method for receiving chapter world of element
     * @return String chapterWorld
     */
    public String scanChapterWorld(){
        for (; ; ) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter chapter world: ");
                String chapterWorld = scanner.nextLine();
                if (chapterWorld.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return chapterWorld;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(1);
            }
        }
    }

    /** Method for making chapter by using methods scanChapterName() and scanChapterWorld() */
    public Chapter scanChapter(){
        return new Chapter(scanChapterName(), scanChapterWorld());
    }

    /** Method for updating the element by it's ID */
    public void update(String in) {
        try {
            int id;
            if (in.indexOf(" ") > 0) {
                id = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            } else {
                id = Integer.parseInt(in);
            }
            System.out.println(id);
            boolean check = false;
            for (SpaceMarine spaceMarine : spaceMarines.values()) {
                if (id == spaceMarine.getId()) {
                    spaceMarine.setName(scanName());
                    check = true;
                }
            }
            if (!check) {
                System.out.println("An element with this id does not exist.");
            } else {
                System.out.println("Name of element updated successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("As an argument you need to enter a number.");
        }
    }

    /** Method for deleting element in the collection by it's key */
    public void removeKey(String in) {
        try {
            int key;
            if (in.indexOf(" ") > 0) {
                key = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            } else {
                key = Integer.parseInt(in);
            }
            boolean check = false;
            for (Map.Entry<Integer, SpaceMarine> entry : spaceMarines.entrySet()) {
                if (entry.getKey().equals(key)) {
                    spaceMarines.remove(entry.getKey());
                    check = true;
                }
            }
            if (!check) {
                System.out.println("An element with this key does not exist.");
            } else {
                System.out.println("Element deleted successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("As an argument you need to enter a number.");
        }
    }

    /** Method for deleting all elements in the collection */
    public void clear() {
        spaceMarines.clear();
        System.out.println("Collection cleared.");
        save();
    }

    /** Method for saving (marshaling) java collection to XML-file */
    public void save() {
        try {
            if (needToClear && xmlFile.length() == 0) {
                // Creation object FileWriter
                FileWriter writer = new FileWriter(xmlFile);
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + "\n" );
                writer.flush();
                writer.close();
            }
            SpaceMarines newSpaceMarines = new SpaceMarines();
            newSpaceMarines.setSpaceMarines(spaceMarines);
            JAXBContext context = JAXBContext.newInstance(SpaceMarines.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //Marshal the persons list in file
            marshaller.marshal(newSpaceMarines, xmlFile);
            System.out.println("Collection saved successfully");
            needToClear = false;
        } catch (JAXBException jaxbException) {
            System.out.println("Violated XML syntax. Check the file and Try again.");
        } catch (IOException ioException) {
            System.out.println("XML File does not exist.");
        }
    }

    /** Method for executing script from external file */
    public void executeScript(String nameOfFile) {
        try {
            System.out.println("WARNING! To avoid recursion, your file cannot contain execute script commands.");
            BufferedReader reader = new BufferedReader(new FileReader(nameOfFile));
            String[] cleanUserCommand;
            String command;
            while((command = reader.readLine()) != null) {
                cleanUserCommand = command.trim().toLowerCase().split(" ", 2);
                switch (cleanUserCommand[0]) {
                    case "":
                        break;
                    case "help":
                        help();
                        break;
                    case "info":
                        info();
                        break;
                    case "show":
                        show();
                        break;
                    case "insert":
                        insert(cleanUserCommand[1]);
                        break;
                    case "update":
                        update(cleanUserCommand[1]);
                        break;
                    case "remove_key":
                        removeKey(cleanUserCommand[1]);
                        break;
                    case "clear":
                        clear();
                        break;
                    case "save":
                        save();
                        break;
                    case "execute_script":
                        System.out.println("This script cannot to contain this command.");
                        break;
                    case "exit":
                        exit();
                        break;
                    case "remove_greater":
                        removeGreater(scanHealth());
                        break;
                    case "replace_if_greater":
                        replaceIfGreater(cleanUserCommand[1]);
                        break;
                    case "remove_greater_key":
                        removeGreaterKey(cleanUserCommand[1]);
                        break;
                    case "group_counting_by_category":
                        groupCountingByCoordinates();
                        break;
                    case "filter_by_chapter":
                        filterByChapter(cleanUserCommand[1]);
                        break;
                    case "filter_starts_with_name":
                        filterStartsWithName(cleanUserCommand[1]);
                        break;
                    default:
                        reader.readLine();
                        break;
                }
                System.out.println("Command is ended.");
            }
            System.out.println("Commands are ended.");
            reader.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Try again.");
        } catch (IOException ioException) {
            System.out.println("File reading exception. Try again.");
        }
    }

    /** Method for switching off the program */
    public void exit() {
        try {
            System.out.println("Program will be finished now. See you again:)");
            TimeUnit.SECONDS.sleep(3);
            System.exit(0);
        } catch (InterruptedException interruptedException) {
            System.out.println("Program will be finished now.");
            System.exit(0);
        }
    }

    /** Method for removing elements from collection if it`s health more than entered health */
    public void removeGreater(int in) {
        boolean check = false;
        for (Map.Entry<Integer, SpaceMarine> entry : spaceMarines.entrySet()){
            SpaceMarine test = entry.getValue();
            if (in < test.getHealth()){
                spaceMarines.remove(entry.getKey());
                System.out.println("Element with key " + entry.getKey() + " deleted successfully.");
                check = true;
            }
        }
        if (!check){
            System.out.println("There are no elements in the collection that exceed the specified one.");
        }
    }

    /** Method for update health field if entered health more than it's health */
    public void replaceIfGreater(String in) {
        try {
            int key;
            if (in.indexOf(" ") > 0) {
                key = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            } else {
                key = Integer.parseInt(in);
            }
            boolean check = false;
            for (Map.Entry<Integer, SpaceMarine> entry : spaceMarines.entrySet()) {
                if (key == entry.getKey()) {
                    check = true;
                    SpaceMarine test = entry.getValue();
                    int newHealth = scanHealth();
                    if (test.getHealth() < newHealth) {
                        test.setHealth(newHealth);
                        spaceMarines.remove(key);
                        spaceMarines.put(key, test);
                    } else {
                        System.out.println("New value is lower than old");
                    }
                }
            }
            if (!check) {
                System.out.println("No such key exists");
            } else {
                System.out.println("Health value updated successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("As an argument you need to enter a number.");
        }
    }

    /** Method for removing elements from collection if it`s value of key more than entered value */
    public void removeGreaterKey(String in) {
        try {
            int key;
            if (in.indexOf(" ") > 0) {
                key = Integer.parseInt(in.substring(0, in.indexOf(" ")));
            } else {
                key = Integer.parseInt(in);
            }
            int check = 0;
            for (Map.Entry<Integer, SpaceMarine> entry : spaceMarines.entrySet()) {
                if (key < entry.getKey()) {
                    spaceMarines.remove(entry.getKey());
                    check += 1;
                }
            }
            if (check == 0) {
                System.out.println("There are no elements in the collection that exceed the specified one.");
            }
            if (check == 1) {
                System.out.println("1 element removed successfully.");
            }
            if (check > 1) {
                System.out.println(check + " elements removed successfully.");
            }
        } catch (NumberFormatException numberFormatException) {
            System.out.println("As an argument you need to enter a number.");
        }
    }

    /** Method for counting amount and grouping elements by it's coordinates */
    public void groupCountingByCoordinates() {
        Map<String, Integer> cords = new HashMap<>();
        for (SpaceMarine spaceMarine : spaceMarines.values()) {
            if (cords.containsKey(spaceMarine.getCoordinates().toString())) {
                cords.put(spaceMarine.getCoordinates().toString(),cords.get(spaceMarine.getCoordinates().toString()) + 1 );
            } else {
                cords.put(spaceMarine.getCoordinates().toString(), 1);
            }
        }
        for (Map.Entry<String, Integer> cord : cords.entrySet()) {
            System.out.print("Elements with coordinates " + cord.getKey() + " : ");
            System.out.print(cord.getValue() + "\n");
        }
    }

    /** Method for printing elements whose chapter field value is equal to it's value of chapter */
    public void filterByChapter(String chapterName) {
        boolean check = false;
        for (SpaceMarine spaceMarine : spaceMarines.values()) {
            if (spaceMarine.getChapter().getChapterName().equals(chapterName)) {
                System.out.println(spaceMarine);
                check = true;
            }
        }
        if (!check) {
            System.out.println("There are no items in the collection with this value.");
        }
    }

    /** Method for printing elements whose name start from it's letters */
    public void filterStartsWithName(String startName) {
        boolean check = false;
        for (SpaceMarine spaceMarine : spaceMarines.values()) {
            if (spaceMarine.getName().substring(0,startName.length()).equals(startName)){
                System.out.println(spaceMarine);
                check = true;
            }
        }
        if (!check) {
            System.out.println("There are no items in the collection whose name begins with the given substring.");
        }
    }

    /** Method for printing initialization date in string representation */
    public String returnDate() {
        return LocalDateTime.now().toString();
    }
}