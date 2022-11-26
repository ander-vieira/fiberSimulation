package com.fibersim.core.raytracing.wavelength.function;

import com.fibersim.core.raytracing.wavelength.Wavelength;

public class CombinedWFunction implements WFunction {
    private final double A;
    private final double B;
    private final WFunction functionA;
    private final WFunction functionB;

    public CombinedWFunction(double a, double b, WFunction functionA, WFunction functionB) {
        A = a;
        B = b;
        this.functionA = functionA;
        this.functionB = functionB;
    }

    @Override
    public double evaluate(Wavelength wavelength) {
        return A*functionA.evaluate(wavelength)+B*functionB.evaluate(wavelength);
    }
}
