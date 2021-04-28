public class Main {
    /**
     * Main class for starting a program
     * @author Kirill Vorobyev
     * @version 1.2
     * @param args - args for program successfully working
     */
    public static void main(String[] args) {
        try {
            Commander commander = new Commander(new CollectionManagement(args[0]));
            commander.consoleMod();
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
            System.out.println("Don`t forget to enter a correct path to file next time.");
            System.exit(1);
        }
    }
}
