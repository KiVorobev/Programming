package data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for describing chapter of element
 */
public class Chapter {
    /** field name */
    private String chapterName; //Field cannot be null, the string cannot be empty
    /** field world */
    private String chapterWorld; //Field cannot be null

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
        return " { name = " + chapterName + ", world = " + chapterWorld + " }";
    }
}