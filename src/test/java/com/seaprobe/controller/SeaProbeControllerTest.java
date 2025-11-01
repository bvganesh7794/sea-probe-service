package com.seaprobe.controller;

import com.seaprobe.dto.CommandRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeaProbeController.class)
public class SeaProbeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CommandRequest request;

    @BeforeEach
    void setUp() {
        request = new CommandRequest();
        request.setStartX(0);
        request.setStartY(0);
        request.setStartDir("NORTH");
        request.setCommands(List.of("F", "R", "F"));
    }

    @Test
    void testExecuteProbe() throws Exception {
        mockMvc.perform(post("/api/seaprobe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition.x").value(1))
                .andExpect(jsonPath("$.finalPosition.y").value(1))
                .andExpect(jsonPath("$.direction").value("EAST"))
                .andExpect(jsonPath("$.visited.length()").value(3));
    }

    @Test
    void testObstacleAvoidance() throws Exception {
        request.setStartX(1);
        request.setStartY(2);
        request.setStartDir("EAST");
        request.setCommands(List.of("F"));

        mockMvc.perform(post("/api/seaprobe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition.x").value(1))
                .andExpect(jsonPath("$.finalPosition.y").value(2))
                .andExpect(jsonPath("$.visited.length()").value(2));
    }

    @Test
    void testOutOfGridMovement() throws Exception {
        request.setStartX(0);
        request.setStartY(4);
        request.setStartDir("NORTH");
        request.setCommands(List.of("F", "F"));

        mockMvc.perform(post("/api/seaprobe/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPosition.x").value(0))
                .andExpect(jsonPath("$.finalPosition.y").value(4))
                .andExpect(jsonPath("$.visited.length()").value(3));
    }
}
