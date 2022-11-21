package com.fibersim.core.raytracing.detector;

import com.fibersim.core.raytracing.common.Ray;
import com.fibersim.core.raytracing.wavelength.Wavelength;
import com.fibersim.core.raytracing.wavelength.provider.WavelengthProvider;

import java.util.HashMap;
import java.util.Map;

public class Detector {
    private final Map<Integer, Double> powers;

    public Detector(WavelengthProvider provider) {
        this.powers = new HashMap<>();

        for(Wavelength wavelength : provider.getWavelengths()) {
            this.powers.put(wavelength.getId(), 0.0);
        }
    }

    public void detect(Ray ray) {
        int id = ray.getWavelength().getId();
        double previousPower;
        if(this.powers.containsKey(id)) {
            previousPower = this.powers.get(id);
        } else {
            previousPower = 0;
        }
        powers.put(id, previousPower+ray.getPower());
    }

    public double getTotalPower() {
        return this.powers.values().stream()
                .reduce(0.0, Double::sum);
    }
}
