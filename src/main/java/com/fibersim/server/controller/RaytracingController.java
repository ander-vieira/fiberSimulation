package com.fibersim.server.controller;

import com.fibersim.server.dto.RaytracingParamsDTO;
import com.fibersim.server.dto.RaytracingResultDTO;
import com.fibersim.server.service.RaytracingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/raytracing")
public class RaytracingController {
    @Autowired
    RaytracingService raytracingService;

    @GetMapping
    public List<RaytracingResultDTO> getSimulations() {
        return raytracingService.getSimulations();
    }

    @GetMapping("/{id}")
    public RaytracingResultDTO getSimulation(@PathVariable String id) {
        return raytracingService.getSimulation(id);
    }

    @PostMapping
    public void addSimulation(@RequestBody RaytracingParamsDTO paramsDTO) {
        raytracingService.addSimulation(paramsDTO);
    }
}
