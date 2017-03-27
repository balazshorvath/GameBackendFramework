package hu.sovaroq.game.squad.lua;

import lombok.Value;

import java.util.Queue;

/**
 * Created by Oryk on 2017. 03. 27..
 */
public class WalkPath {
    private final Queue<Edge> edges;

    public WalkPath(Queue<Edge> edges) {
        this.edges = edges;
    }

    public Edge current() {
        return edges.peek();
    }

    public Edge next() {
        edges.poll();
        return edges.peek();
    }

    @Value
    public static class Edge {
        public final double x, y;
    }
}
