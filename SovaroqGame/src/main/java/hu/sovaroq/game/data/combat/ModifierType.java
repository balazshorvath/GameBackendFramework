package hu.sovaroq.game.data.combat;

import com.sun.org.apache.xpath.internal.operations.Mod;

import java.util.HashMap;
import java.util.Map;

public enum ModifierType {

    // Damaging Stats
    PHYSICAL_DAMAGE(0),
    FIRE_DAMAGE(1),
    FROST_DAMAGE(2),
    POISON_DAMAGE(3),
    ELECTRICAL_DAMAGE(4),

    // Health Stats
    HP(5),
    MOVEMENT_SPEED(6),
    ATTACK_SPEED(7),

    // Defensive Stats
    ARMOR(8),
    FIRE_RESISTANCE(9),
    FROST_RESISTANCE(10),
    POISON_RESISTANCE(11),
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

    public static Map<ModifierType, Double> createEmptyStats(){
        Map<ModifierType, Double> stats = new HashMap<>();
        for(ModifierType t : ModifierType.values()){
            stats.put(t, 0.0);
        }
        return stats;
    }
}
