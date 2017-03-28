package hu.sovaroq.game.data;

import static javax.persistence.GenerationType.AUTO;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import hu.sovaroq.game.data.modifiers.Modifier;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "TalentBase")
@Data
@ToString
public class TalentBase {
	
	@Id
	@GeneratedValue(strategy = AUTO)
	Long talentId;
	
	@Column(length = 255, nullable = false)
	String name;
	
	@Column(length = 255, nullable = false)
	String description;

	
}
