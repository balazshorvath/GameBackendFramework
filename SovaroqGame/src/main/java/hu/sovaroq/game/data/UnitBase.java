package hu.sovaroq.game.data;

import static javax.persistence.GenerationType.AUTO;

import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyClass;
import javax.persistence.Table;

import hu.sovaroq.game.data.combat.AttackBase;
import hu.sovaroq.game.data.combat.DefenseType;
import hu.sovaroq.game.data.combat.ModifierType;
import lombok.Data;
import lombok.ToString;

@Entity
@Table
@Data
@ToString
public class UnitBase {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long unitId;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "attackbase_unitbase", joinColumns = {@JoinColumn(name = "unitId")}, inverseJoinColumns = {@JoinColumn(name = "attackId")})
    private Set<AttackBase> baseAttacks;
    
    @MapKeyClass(value = ModifierType.class)
    @ElementCollection(targetClass = Double.class)
    private Map<ModifierType, Double> stats;
    
    @Column(length = 50, nullable = false)
    private String scriptFile;

    public UnitBase() {
    }

    public UnitBase(String name, String description, Set<AttackBase> baseAttacks, Map<ModifierType, Double> stats, String scriptFile) {
        this.name = name;
        this.description = description;
        this.baseAttacks = baseAttacks;
        this.stats = stats;
        this.scriptFile = scriptFile;
    }
}

