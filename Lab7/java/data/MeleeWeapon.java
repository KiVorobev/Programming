package data;

import javax.persistence.Embeddable;

/**
 * @author Kirill Vorobyev
 * @version 1.0
 * Enum class for describing melee weapon of element
 */
@Embeddable
public enum MeleeWeapon {
    CHAIN_SWORD,
    CHAIN_AXE,
    POWER_BLADE,
    POWER_FIST
}