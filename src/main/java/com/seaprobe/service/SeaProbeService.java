package com.seaprobe.service;

import com.seaprobe.model.Direction;
import com.seaprobe.model.Grid;
import com.seaprobe.model.Position;

import java.util.*;

public class SeaProbeService {
    private final Grid grid;
    private Position position;
    private Direction direction;
    private final List<Position> visited = new ArrayList<>();

    private static final Map<Direction, Direction> LEFT = Map.of(
            Direction.NORTH, Direction.WEST,
            Direction.WEST, Direction.SOUTH,
            Direction.SOUTH, Direction.EAST,
            Direction.EAST, Direction.NORTH
    );

    private static final Map<Direction, Direction> RIGHT = Map.of(
            Direction.NORTH, Direction.EAST,
            Direction.EAST, Direction.SOUTH,
            Direction.SOUTH, Direction.WEST,
            Direction.WEST, Direction.NORTH
    );

    private static final Map<Direction, int[]> MOVES = Map.of(
            Direction.NORTH, new int[]{0, 1},
            Direction.EAST, new int[]{1, 0},
            Direction.SOUTH, new int[]{0, -1},
            Direction.WEST, new int[]{-1, 0}
    );

    public SeaProbeService(Grid grid, Position start, Direction dir) {
        this.grid = grid;
        this.position = start;
        this.direction = dir;
        visited.add(start);
    }

    public void execute(List<String> commands) {
        for (String cmd : commands) {
            switch (cmd) {
                case "F" -> move(true);
                case "B" -> move(false);
                case "L" -> direction = LEFT.get(direction);
                case "R" -> direction = RIGHT.get(direction);
            }
        }
    }

    private void move(boolean forward) {
        int[] move = MOVES.get(direction);
        int dx = forward ? move[0] : -move[0];
        int dy = forward ? move[1] : -move[1];
        Position next = position.move(dx, dy);

        visited.add(new Position(next.getX(), next.getY()));

        if (grid.isInside(next) && !grid.isObstacle(next)) {
            position = new Position(next.getX(), next.getY());
        }
    }



    public Position getPosition() { return position; }
    public Direction getDirection() { return direction; }
    public List<Position> getVisited() { return visited; }
}
