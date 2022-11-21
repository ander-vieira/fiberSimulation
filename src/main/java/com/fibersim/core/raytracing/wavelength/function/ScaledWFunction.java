package com.fibersim.core.raytracing.wavelength.function;

import com.fibersim.core.raytracing.wavelength.Wavelength;

public class ScaledWFunction implements WFunction {
    private final WFunction function;
    private final double scale;

    public ScaledWFunction(WFunction function, double scale) {
        this.function = function;
        this.scale = scale;
    }

    @Override
    public double evaluate(Wavelength wavelength) {
        return function.evaluate(wavelength)*scale;
    }
}
