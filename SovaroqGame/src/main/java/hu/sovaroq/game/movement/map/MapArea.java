package hu.sovaroq.game.movement.map;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by balazs_horvath on 4/25/2017.
 */
public class MapArea {
    /**
     * TODO: this is not defined yet.
     * Area type, could be the model, or simply it is a desert, tundra, whatever.
     */
    @Getter
    private final String type;
    /**
     * The index of the area in a map. For example {@link GameMap}.
     */
    @Getter
    private final int index;
    /**
     * This property shows which side of the area is "open".
     * If there's a null, you shall not pass.
     * If it's a valid object, you can pass through.
     */
    private final Map<Direction, MapArea> neighbours = Direction.initial();
    /**
     *
     */
    @Getter
    private final List<BuildArea> buildAreas;
    /**
     * The parameter, that defines the virtual resolution of an area.
     * Used in pair with {@link #heightMap}.
     */
    @Getter
    private final int width;
    /**
     * If the number is > 100, nothing can step on that "pixel".
     * The resolution:
     *      x: {@link #width}.
     *      y: heightMap.length/{@link #width}.
     */
    @Getter
    private final byte[] heightMap;

    public MapArea(String type, int index, List<BuildArea> buildAreas, int width, byte[] heightMap) {
        this.type = type;
        this.index = index;
        this.buildAreas = buildAreas;
        this.width = width;
        this.heightMap = heightMap;
    }


    public MapArea getNeighbour(Direction direction){
        return neighbours.get(direction);
    }

    public MapArea setNeighbour(Direction direction, MapArea neighbour){
        return neighbours.put(direction, neighbour);
    }


    public enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN;

        public static Map<Direction, MapArea> initial(){
            Map<Direction, MapArea> def = new HashMap<>(4);
            for (Direction direction : values()) {
                def.put(direction, null);
            }
            return def;
        }
    }

}
