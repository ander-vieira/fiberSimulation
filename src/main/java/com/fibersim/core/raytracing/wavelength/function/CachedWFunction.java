package com.fibersim.core.raytracing.wavelength.function;

import com.fibersim.core.raytracing.wavelength.Wavelength;

import java.util.HashMap;
import java.util.Map;

public class CachedWFunction implements WFunction {
    private final WFunction function;
    private final Map<Integer, Double> cachedValues;

    public CachedWFunction(WFunction function) {
        this.function = function;
        this.cachedValues = new HashMap<>();
    }

    @Override
    public double evaluate(Wavelength wavelength) {
        int id = wavelength.getId();

        if(cachedValues.containsKey(id)) {
            return this.cachedValues.get(id);
        } else {
            double value = this.function.evaluate(wavelength);
            this.cachedValues.put(id, value);
            return value;
        }
    }
}
