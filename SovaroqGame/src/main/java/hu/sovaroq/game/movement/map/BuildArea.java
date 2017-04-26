package hu.sovaroq.game.movement.map;

import hu.sovaroq.game.data.BuildingBase;

/**
 *
 *
 * Created by balazs_horvath on 4/25/2017.
 */
public class BuildArea {
    /**
     * Type of the area/building, that can be built here.
     */
    private BuildingBase.BuildingAreaType type;
    /**
     * A {@link BuildArea} is part of the {@link MapArea}.
     * The x, y coordinates define the top-left corner of the area, which can be built.
     */
    private int x, y;
}
