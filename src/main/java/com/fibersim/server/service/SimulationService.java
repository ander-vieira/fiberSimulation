package com.fibersim.server.service;

import com.fibersim.core.RaytracingSimulation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimulationService {
    private final Map<String, RaytracingSimulation> models = new HashMap<>();

    public RaytracingSimulation getSimulation(String id) {
        return models.get(id);
    }

    public List<RaytracingSimulation> getSimulations() {
        return models.values().stream().toList();
    }

    public void addSimulation(RaytracingSimulation model) {
        models.put(model.getId(), model);
    }
}
