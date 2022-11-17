package com.fibersim.core.model.source;

import com.fibersim.core.model.common.Ray;

public interface Source {
    int numRays();
    Ray getRay(int index);
}
