package com.fibersim.core.data.medium;

import com.fibersim.core.model.wavelength.function.WFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Medium {
    private final String name;
    private final WFunction N;
    private final WFunction alpha;
}
