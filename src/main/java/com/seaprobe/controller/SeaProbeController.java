package com.seaprobe.controller;

import com.seaprobe.dto.CommandRequest;
import com.seaprobe.dto.SeaProbeResponse;
import com.seaprobe.model.*;
import com.seaprobe.service.SeaProbeService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/seaprobe")
public class SeaProbeController {

    @PostMapping("/execute")
    public SeaProbeResponse execute(@RequestBody CommandRequest request) {
        Grid grid = new Grid(5, 5, Set.of(new Position(2, 2)));
        SeaProbeService service = new SeaProbeService(
                grid,
                new Position(request.getStartX(), request.getStartY()),
                Direction.valueOf(request.getStartDir().toUpperCase())
        );
        service.execute(request.getCommands());
        return new SeaProbeResponse(service.getPosition(), service.getDirection(), service.getVisited());
    }
}
