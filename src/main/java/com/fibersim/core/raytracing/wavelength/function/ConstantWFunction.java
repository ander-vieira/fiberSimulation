package com.fibersim.core.raytracing.wavelength.function;

import com.fibersim.core.raytracing.wavelength.Wavelength;

public class ConstantWFunction implements WFunction {
    private final double value;

    public ConstantWFunction(double value) {
        this.value = value;
    }

    @Override
    public double evaluate(Wavelength wavelength) {
        return value;
    }
}
