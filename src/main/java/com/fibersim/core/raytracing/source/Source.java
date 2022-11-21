package com.fibersim.core.raytracing.source;

import com.fibersim.core.raytracing.common.Ray;

public interface Source {
    int numRays();
    Ray getRay(int index);
}
