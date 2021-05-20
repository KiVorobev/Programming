package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for describing chapter of element
 */
@XmlType(name = "chapter")
@XmlRootElement
public class Chapter {
    /** field name */
    @XmlElement
    private String chapterName; //Поле не может быть null, Строка не может быть пустой
    /** field world */
    @XmlElement
    private String chapterWorld; //Поле не может быть null

    /** Constructor */
    public Chapter(String name, String world) {
        this.chapterName = name;
        this.chapterWorld = world;
    }

    /** Default constructor */
    public Chapter() {}

    public String getChapterName() {
        return chapterName;
    }

    public void setName(String name) {
        this.chapterName = name;
    }

    public String getChapterWorld() {
        return chapterWorld;
    }

    public void setWorld(String world) {
        this.chapterWorld = world;
    }

    /** Method for printing this field into a string representation */
    @Override
    public String toString(){
        return "chapter { name = " + chapterName + ", world = " + chapterWorld + " }";
    }
}