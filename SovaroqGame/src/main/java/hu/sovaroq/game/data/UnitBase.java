package hu.sovaroq.game.data;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table
@Data
@ToString
public class UnitBase {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long unitid;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String description;
    @Column(nullable = false)
    private long baseHP;
    @Column(nullable = false)
    private long baseAttackDamage;
    @Column(nullable = false)
    private double baseMovementSpeed;

    public UnitBase() {
    }

}

