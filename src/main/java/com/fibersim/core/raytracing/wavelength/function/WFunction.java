package com.fibersim.core.raytracing.wavelength.function;

import com.fibersim.core.raytracing.wavelength.Wavelength;

public interface WFunction {
    double evaluate(Wavelength wavelength);
}
