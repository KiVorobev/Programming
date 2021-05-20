package data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for describing coordinates of element
 */
@XmlType (name = "coordinates")
@XmlRootElement
public class Coordinates {
    /** field x */
    @XmlElement
    private int xCord;
    /** field y */
    @XmlElement
    private Integer yCord; //Maximum field value: 941, the field cannot be null

    /** Constructor */
    public Coordinates(int x, Integer y) {
        this.xCord = x;
        this.yCord = y;
    }

    public int getXCord() {
        return xCord;
    }

    public void setX(int x) {
        this.xCord = x;
    }

    public Integer getYCord() {
        return yCord;
    }

    public void setY(Integer y) {
        this.yCord = y;
    }

    /** Method for printing this field into a string representation */
    @Override
    public String toString(){
        return "{ x = " + xCord + ", y = " + yCord + " }";
    }

    /** Default constructor */
    public Coordinates() {}
}
