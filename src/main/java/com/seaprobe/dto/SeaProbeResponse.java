package com.seaprobe.dto;

import com.seaprobe.model.Direction;
import com.seaprobe.model.Position;

import java.util.List;

public class SeaProbeResponse {
    private final Position finalPosition;
    private final Direction direction;
    private final List<Position> visited;

    public SeaProbeResponse(Position finalPosition, Direction direction, List<Position> visited) {
        this.finalPosition = finalPosition;
        this.direction = direction;
        this.visited = visited;
    }

    public Position getFinalPosition() { return finalPosition; }
    public Direction getDirection() { return direction; }
    public List<Position> getVisited() { return visited; }
}
