package hu.sovaroq.game.data.modifiers;

import hu.sovaroq.game.data.combat.ModifierType;
import lombok.Data;

@Data
public class Modifier {
	
	ModifierType type;
	
	Double value;
}
