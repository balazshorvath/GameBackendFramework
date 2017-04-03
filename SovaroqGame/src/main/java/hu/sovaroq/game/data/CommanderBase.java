package hu.sovaroq.game.data;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Table(name = "CommanderBase")
@Data
@ToString
public class CommanderBase {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column
    private Long commanderId;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 255, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buildingbase_commanderbase", joinColumns = {@JoinColumn(name = "commanderId")}, inverseJoinColumns = {@JoinColumn(name = "buildingId")})
    private Set<BuildingBase> availableBuildings;

    public CommanderBase(String name, String description, Set<BuildingBase> availableBuildings) {
        this.name = name;
        this.description = description;
        this.availableBuildings = availableBuildings;
    }

    public CommanderBase() {
    }
}
