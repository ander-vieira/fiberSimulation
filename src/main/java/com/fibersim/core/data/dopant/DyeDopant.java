package com.fibersim.core.data.dopant;

import com.fibersim.core.model.wavelength.function.WFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DyeDopant {
    private final String name;
    private final double N;
    private final WFunction sigmaAbs;
    private final WFunction sigmaemi;
    private final double quantumYield;
}
