package com.fibersim.core.resources.medium;

import com.fibersim.core.raytracing.wavelength.function.WFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Medium {
    private final String name;
    private final WFunction N;
    private final WFunction alpha;
}
