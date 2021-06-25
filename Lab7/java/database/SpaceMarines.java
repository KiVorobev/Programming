package database;

import data.AstartesCategory;
import data.MeleeWeapon;
import data.Weapon;

public class SpaceMarines {
    /**
     * field key of this element
     */
    private int key;
    /**
     * Field ID
     */
    private int id; //The value of the field must be greater than 0, and the value of this field must be unique
    //The value of this field should be generated automatically
    /**
     * Field name
     */
    private String name; //Field cannot be null, the string cannot be empty
    /**
     * field x
     */
    private int xCord;
    /**
     * field y
     */
    private Integer yCord; //Maximum field value: 941, the field cannot be null
    /**
     * Field creation date
     */
    private String creationDate; //Field cannot be null, the value of this field should be generated automatically
    /**
     * Field health
     */
    private Integer health; //Field can be null, the field value must be greater than 0
    /**
     * Field category
     */
    private AstartesCategory category; //Field can be null
    /**
     * Field weapon type
     */
    private Weapon weaponType; //Field can be null
    /**
     * Field melee weapon
     */
    private MeleeWeapon meleeWeapon; //Field can be null
    /**
     * field name of chapter
     */
    private String chapterName; //Field cannot be null, the string cannot be empty
    /**
     * field world of chapter
     */
    private String chapterWorld; //Field cannot be null
    /**
     * field host user
     */
    private String user;


    public SpaceMarines() {

    }

    /**
     * Constructor for making a space marine
     */
    public SpaceMarines(int key, int id, String name, int xcord, Integer ycord, String creationDate, Integer health,
                        AstartesCategory category, Weapon weaponType, MeleeWeapon meleeWeapon, String chaptername,
                        String chapterworld, String user) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.xCord = xcord;
        this.yCord = ycord;
        this.creationDate = creationDate;
        this.health = health;
        this.category = category;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapterName = chaptername;
        this.chapterWorld = chapterworld;
        this.user = user;
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

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public Integer getyCord() {
        return yCord;
    }

    public void setyCord(Integer yCord) {
        this.yCord = yCord;
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

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterWorld() {
        return chapterWorld;
    }

    public void setChapterWorld(String chapterWorld) {
        this.chapterWorld = chapterWorld;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Method for printing this field into a string representation
     */
    @Override
    public String toString() {
        return key + " SpaceMarines{" +
                "\nid = " + id +
                "\nname = " + name +
                "\ncoordinates { x = " + xCord + " , y = " + yCord + " }" +
                "\ncreationDate = " + creationDate +
                "\nhealth = " + health +
                "\ncategory = " + category +
                "\nweaponType = " + weaponType +
                "\nmeleeWeapon = " + meleeWeapon +
                "\nchapter { name = " + chapterName + " , world = " + chapterWorld + " }" +
                "\nuser = " + user +
                "\n}" +
                "\n";
    }
}
