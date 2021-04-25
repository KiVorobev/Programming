package data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "spaceMarine")
@XmlAccessorType(XmlAccessType.NONE)
public class SpaceMarine {
    /** Field ID */
    @XmlElement
    private int id; //The value of the field must be greater than 0, and the value of this field must be unique
                    //The value of this field should be generated automatically
    /** Field name */
    @XmlElement
    private String name; //Field cannot be null, the string cannot be empty
    /** Field coordinates */
    @XmlElement
    private Coordinates coordinates; //Field cannot be null
    /** Field creation date */
    @XmlElement
    private String creationDate; //Field cannot be null, the value of this field should be generated automatically
    /** Field health */
    @XmlElement
    private Integer health; //Field can be null, the field value must be greater than 0
    /** Field category */
    @XmlElement
    private AstartesCategory category; //Field can be null
    /** Field weapon type */
    @XmlElement
    private Weapon weaponType; //Field can be null
    /** Field melee weapon */
    @XmlElement
    private MeleeWeapon meleeWeapon; //Field can be null
    /** Field chapter */
    @XmlElement
    private Chapter chapter; //Field cannot be null

    public SpaceMarine() {

    }

    /** Constructor for making a space marine */
    public SpaceMarine(int id, String name, Coordinates coordinates, String creationDate, Integer health,
                       AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public AstartesCategory getCategory() {
        return category;
    }

    public void setCategory(AstartesCategory category) {
        this.category = category;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    /** Method for printing this field into a string representation */
    @Override
    public String toString(){
        return "SpaceMarine{" +
                "\nid = " + id +
                "\nname = " + name +
                "\n" + coordinates +
                "\ncreationDate = " + creationDate +
                "\nhealth = " + health +
                "\ncategory = " + category +
                "\nweaponType = " + weaponType +
                "\nmeleeWeapon = " + meleeWeapon +
                "\n" + chapter +
                "\n}";
    }
}