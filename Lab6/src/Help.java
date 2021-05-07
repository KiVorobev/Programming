import java.util.Map;

public class Help {
    
    /** Method for printing manual for user */
    public void help() {
        for (Map.Entry<String, String> entry : infoCommands.entrySet()) {
            System.out.println(entry.getKey() + entry.getValue());
        }
    }
}
