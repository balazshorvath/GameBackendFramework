package hu.sovaroq.game.core.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BuildingBase.class)
public abstract class BuildingBase_ {

	public static volatile SetAttribute<BuildingBase, UnitBase> availableUnits;
	public static volatile SingularAttribute<BuildingBase, String> name;
	public static volatile SingularAttribute<BuildingBase, String> description;
	public static volatile SingularAttribute<BuildingBase, Long> baseAttackDamage;
	public static volatile SingularAttribute<BuildingBase, Long> baseHP;
	public static volatile SingularAttribute<BuildingBase, Long> buildingid;

}

