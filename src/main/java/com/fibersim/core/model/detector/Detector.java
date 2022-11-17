package com.fibersim.core.model.detector;

import com.fibersim.core.model.common.Ray;

public class Detector {
    private double power;

    public Detector() {
        this.power = 0;
    }

    public void detect(Ray ray) {
        power += ray.getPower();
    }

    public double getTotalPower() {
        return this.power;
    }
}
