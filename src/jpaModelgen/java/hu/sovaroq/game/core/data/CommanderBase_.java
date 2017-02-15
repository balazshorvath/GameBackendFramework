package hu.sovaroq.game.core.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CommanderBase.class)
public abstract class CommanderBase_ {

	public static volatile SingularAttribute<CommanderBase, String> name;
	public static volatile SingularAttribute<CommanderBase, String> description;
	public static volatile SetAttribute<CommanderBase, BuildingBase> availableBuildings;
	public static volatile SingularAttribute<CommanderBase, Long> commanderid;

}

