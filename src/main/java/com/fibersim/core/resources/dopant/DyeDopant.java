package com.fibersim.core.resources.dopant;

import com.fibersim.core.raytracing.wavelength.function.WFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DyeDopant {
    private final String name;
    private final double concentration;
    private final double quantumYield;
    private final WFunction sigmaAbs;
    private final WFunction sigmaemi;
}
