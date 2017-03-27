package hu.sovaroq.game.data.combat;

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
    private Long attackid;

    // TODO replace this with Modifier/Type. Easier to match.
    @Column
    private DamageType damageType;
	
    @Column
    private Integer range;
	
    @Column
    private Integer cooldown;
	
    @Column
    private Integer baseDamage;
    
    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;
    
    @Column(length = 50, nullable = false)
    private String scriptFile;
	
}
