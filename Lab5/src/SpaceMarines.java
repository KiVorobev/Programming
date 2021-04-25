import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import data.*;

    /**
     * @author Kirill Vorobyev
     * @version 1.1
     * Class for describing a collection in a marshaling-ready view
     */

    @XmlRootElement (name = "spaceMarines")
    @XmlAccessorType (XmlAccessType.NONE)
    public class SpaceMarines {
        /**
         * Field persons - map for keeping collection
         */
        @XmlElement (name = "spaceMarine")
        private List<SpaceMarine> spaceMarines = null;

        /**
         * Method for get spaceMarines map
         *
         * @return Map<Integer, SpaceMarine> spaceMarines
         */
        public List<SpaceMarine> getSpaceMarines() {
            return spaceMarines;
        }

        /**
         * Method for passing a value to the spaceMarines hashmap
         */
        public void setSpaceMarines(List<SpaceMarine> spaceMarines) {
            this.spaceMarines = spaceMarines;
        }
    }
