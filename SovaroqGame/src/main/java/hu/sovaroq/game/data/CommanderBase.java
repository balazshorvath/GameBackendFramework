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

    @Column(length = 50, nullable = false, unique = true)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "buildingbase_commanderbase", joinColumns = {@JoinColumn(name = "commanderid")}, inverseJoinColumns = {@JoinColumn(name = "buildingid")})
    private Set<BuildingBase> availableBuildings;

}
