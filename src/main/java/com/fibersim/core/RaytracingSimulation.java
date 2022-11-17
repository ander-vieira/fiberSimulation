package com.fibersim.core;

import com.fibersim.core.model.RaytracingModel;
import com.fibersim.core.model.source.Source;
import lombok.Getter;
import java.util.UUID;

public class RaytracingSimulation {
    @Getter
    private final String id;
    @Getter
    private final RaytracingModel model;
    private int totalRays;
    private int currentRays;

    public RaytracingSimulation() {
        this.id = UUID.randomUUID().toString();
        this.model = new RaytracingModel();
        this.currentRays = 0;
        this.totalRays = 1;
    }

    public void initialize() {
        this.totalRays = this.model.getSources().stream().map(Source::numRays).reduce(0, Integer::sum);
        this.currentRays = 0;
    }

    public void increment() {
        this.currentRays++;
    }

    public int progress() {
        return (this.currentRays*100)/this.totalRays;
    }

    public boolean finished() {
        return this.currentRays >= this.totalRays;
    }

    public double getTotalPower() {
        return this.getModel().getDetector().getTotalPower();
    }
}
