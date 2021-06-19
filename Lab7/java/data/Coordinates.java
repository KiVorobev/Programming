package data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for describing coordinates of element
 */
@Embeddable
public class Coordinates {
    /** field x */
    @Column
    private int xCord;
    /** field y */
    @Column(nullable=false)
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