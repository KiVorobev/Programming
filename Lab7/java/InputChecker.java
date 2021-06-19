import data.*;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for receiving data for elements of collection
 * @author Kirill Vorobyev
 * @version 1.0
 */
public class InputChecker {

    /** Method for printing initialization date in string representation */
    public String returnDate() {
        return LocalDateTime.now().toString();
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
                System.exit(0);
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
                String test = scanner.nextLine().trim();
                if (test.equals("")){
                    System.out.println("This value cannot be empty. Try again.");
                    continue;
                } else {
                    int x;
                    if (test.indexOf(" ") > 0) {
                        x = Integer.parseInt(test.substring(0, test.indexOf(" ")));
                    } else {
                        x = Integer.parseInt(test);
                    }
                    return x;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Argument must be of type integer. Try again.");
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
                String test = scanner.nextLine().trim();
                if (test.equals("")) {
                    System.out.println("This value cannot be empty. Try again.");
                    continue;
                } else {
                    int y;
                    if (test.indexOf(" ") > 0) {
                        y = Integer.parseInt(test.substring(0, test.indexOf(" ")));
                    } else {
                        y = Integer.parseInt(test);
                    }
                    if (y > 941) {
                        System.out.println("Max value is 941. Try again.");
                        continue;
                    }
                    return y;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Argument must be of type integer. Try again.");
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
                String test = scanner.nextLine().trim();
                if (test.equals("")) {
                    System.out.println("This value cannot be empty. Try again.");
                    continue;
                } else {
                    int health;
                    if (test.indexOf(" ") > 0) {
                        health = Integer.parseInt(test.substring(0, test.indexOf(" ")));
                    } else {
                        health = Integer.parseInt(test);
                    }
                    if (health <= 0) {
                        System.out.println("This value must be greater than 0. Try again");
                        continue;
                    }
                    return health;
                }
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be integer. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Argument must be of type integer. Try again.");
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
                System.out.println("Variants: ASSAULT, TACTICAL, CHAPLAIN.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String category = scanner.nextLine().toUpperCase().trim();
                switch (category) {
                    case "":
                        return null;
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
                System.exit(0);
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
                System.out.println("Variants: BOLTGUN, PLASMA_GUN, COMBI_PLASMA_GUN, GRENADE_LAUNCHER.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String weapon = scanner.nextLine().toUpperCase().trim();
                switch (weapon) {
                    case "":
                        return null;
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
                System.exit(0);
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
                System.out.println("Variants: CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST.");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                String meleeWeapon = scanner.nextLine().toUpperCase().trim();
                switch (meleeWeapon) {
                    case "":
                        return null;
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
                System.out.println("You should to enter CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST. Try again.");
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be a words (CHAIN_SWORD, CHAIN_AXE, POWER_BLADE, POWER_FIST). Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully. ");
                System.exit(0);
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
                String chapterName = scanner.nextLine().trim();
                if (chapterName.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return chapterName;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
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
                String chapterWorld = scanner.nextLine().trim();
                if (chapterWorld.equals("")) {
                    System.out.println("This value cannot be empty. Try again");
                    continue;
                }
                return chapterWorld;
            } catch (InputMismatchException inputMismatchException) {
                System.out.println("This value must be string. Try again.");
            } catch (NoSuchElementException noSuchElementException) {
                System.out.println("Program was stopped successfully.");
                System.exit(0);
            }
        }
    }

    /** Method for making chapter by using methods scanChapterName() and scanChapterWorld() */
    public Chapter scanChapter(){
        return new Chapter(scanChapterName(), scanChapterWorld());
    }
}