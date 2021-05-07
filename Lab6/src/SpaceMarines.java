import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import data.*;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Class for describing a collection in a marshaling-ready view
 */

@XmlRootElement (name = "spaceMarines")
@XmlAccessorType (XmlAccessType.NONE)
public class SpaceMarines {

    /**
     * Field persons - map for keeping collection
     */
    @XmlElement (name = "spaceMarine")
    private Map<Integer, SpaceMarine> spaceMarine = new HashMap<Integer, SpaceMarine>();

    /**
     * Method for get spaceMarines map
     *
     * @return Map<Integer, SpaceMarine> spaceMarines
     */
    public Map<Integer, SpaceMarine> getSpaceMarines() {
        return spaceMarine;
    }

    /**
     * Method for passing a value to the spaceMarines hashmap
     */
    public void setSpaceMarines(Map<Integer, SpaceMarine> spaceMarines) {
        this.spaceMarine = spaceMarines;
    }
}
