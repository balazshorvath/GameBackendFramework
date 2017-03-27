package hu.sovaroq.game.data.modifiers;

import hu.sovaroq.game.data.combat.ModifierType;
import lombok.Data;

/**
 * A Modifier is a buff, or de-buff affecting entities during the game.
 * It represents a single stat modification for a period of time.
 * Duration might be 0, or null. In that case it's permanent.
 */
@Data
public class Modifier {
    ModifierType type;
    Double value;
    Long duration;
}
