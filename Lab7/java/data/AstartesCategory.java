package data;

import javax.persistence.Embeddable;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Enum class for describing category of element
 */
@Embeddable
public enum AstartesCategory {
    ASSAULT,
    TACTICAL,
    CHAPLAIN
}