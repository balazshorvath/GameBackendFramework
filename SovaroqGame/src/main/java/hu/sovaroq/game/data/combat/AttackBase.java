package hu.sovaroq.game.data.combat;

import hu.sovaroq.game.data.modifiers.Modifier;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "AttackBase")
@Data
@ToString
public class AttackBase {
	
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long attackId;

    @Column
    private Double area;
    @Column
    private Double range;
    @Column
    private Double cooldown;

    @Column
    private ModifierType type;
    @Column
    private Double value;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;
    
    @Column(length = 50, nullable = false)
    private String scriptFile;
	
}
