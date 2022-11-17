package com.fibersim.server.mapper;

import com.fibersim.server.dto.RaytracingResultDTO;
import com.fibersim.core.RaytracingSimulation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaytracingResultMapper {
    public RaytracingResultDTO map(RaytracingSimulation raytracingSimulation) {
        return RaytracingResultDTO.builder()
                .id(raytracingSimulation.getId())
                .finished(raytracingSimulation.finished())
                .progress(raytracingSimulation.progress())
                .lightPower(raytracingSimulation.getTotalPower())
                .build();
    }
    public List<RaytracingResultDTO> map(List<RaytracingSimulation> raytracingSimulations) {
        return raytracingSimulations.stream().map(this::map).toList();
    }
}
