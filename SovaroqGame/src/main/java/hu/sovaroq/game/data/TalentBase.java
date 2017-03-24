package hu.sovaroq.game.data;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

import hu.sovaroq.game.data.modifiers.Modifier;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "TalentBase")
@Data
@ToString
public class TalentBase {
	
	String name;
	
	String description;

	
}
