package hu.sovaroq.game.data.combat;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "AttackBase")
@Data
@ToString
public class AttackBase {
	
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long attackid;

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
