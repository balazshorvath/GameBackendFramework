package hu.sovaroq.game.data.combat;

public enum ModifierType {

    // Damaging Stats
    PHYSICAL_DAMAGE(0),
    FIRE_DAMAGE(1),
    FROST_DAMAGE(2),
    POISION_DAMAGE(3),
    ELECTRICAL_DAMAGE(4),

    // Health Stats
    HP(5),
    MOVEMENTSPEED(6),
    ATTACKSPEED(7),

    // Defensive Stats
    ARMOR(8),
    FIRE_RESISTANCE(9),
    FROST_RESISTANCE(10),
    POISION_RESISTANCE(11),
    ELECTRICAL_RESISTANCE(12),

    // States
    STUN(13),
    POISON(14);

    final Integer value;

    ModifierType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }
}
