package com.fibersim.core.model.wavelength;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WValue {
    private final Wavelength wavelength;
    private final double value;
}
