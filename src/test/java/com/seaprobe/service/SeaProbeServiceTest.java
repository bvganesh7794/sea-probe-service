package com.seaprobe.service;

import com.seaprobe.model.Grid;
import com.seaprobe.model.Position;
import com.seaprobe.model.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class SeaProbeServiceTest {

    private SeaProbeService service;
    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(5, 5, Set.of(new Position(2, 2)));
        service = new SeaProbeService(grid, new Position(0, 0), Direction.NORTH);
    }

    @Test
    void testStartPosition() {
        Assertions.assertEquals(new Position(0, 0), service.getPosition());
        Assertions.assertEquals(Direction.NORTH, service.getDirection());
    }

    @Test
    void testMoveForward() {
        service.execute(List.of("F"));
        Assertions.assertEquals(new Position(0, 1), service.getPosition());
    }

    @Test
    void testTurnLeft() {
        service.execute(List.of("L"));
        Assertions.assertEquals(Direction.WEST, service.getDirection());
    }

    @Test
    void testTurnRight() {
        service.execute(List.of("R"));
        Assertions.assertEquals(Direction.EAST, service.getDirection());
    }

    @Test
    void testStayWithinGrid() {
        service.execute(List.of("F", "F", "F", "F", "F", "F"));
        Assertions.assertEquals(new Position(0, 4), service.getPosition());
    }

    @Test
    void testAvoidObstacle() {
        SeaProbeService s = new SeaProbeService(grid, new Position(1, 2), Direction.EAST);
        s.execute(List.of("F"));
        Assertions.assertEquals(new Position(1, 2), s.getPosition());
    }

    @Test
    void testVisitedCoordinates() {
        service.execute(List.of("F", "R", "F"));
        Assertions.assertEquals(List.of(
                new Position(0, 0),
                new Position(0, 1),
                new Position(1, 1)
        ), service.getVisited());
    }
}
