package data;

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

/**
 * @author Kirill Vorobyev
 * @version 1.2
 * Class which realised user`s commands
 */
public class Manager {

    /** TreeMap collection for keeping a collection as java-object */
    private TreeMap<Integer, SpaceMarine> spaceMarines = new TreeMap<>();
    /** Field for saving date of initialization the collection */
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
        infoCommands.put("replace_if_greater number_of_key", " - Replace the value by key if the new value is greater than the old one");
        infoCommands.put("remove_greater_key number_of_key", " - Remove all items from the collection whose key exceeds the specified value");
        infoCommands.put("group_counting_by_coordinates", " - Group the collection items by the coordinates field value, output the number of items in each group");
        infoCommands.put("filter_by_chapter chapter_name", " - Output elements whose chapter field value is equal to the specified value");
        infoCommands.put("filter_starts_with_name name", " - Output elements whose name field value starts with the specified substring");
    }

    /** Constructor for checking a path to file existence and file readiness to work */
    public Manager(String pathToXML) throws IOException {
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

    public File getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }
}