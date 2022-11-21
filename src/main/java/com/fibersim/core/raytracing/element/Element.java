package com.fibersim.core.raytracing.element;

import com.fibersim.core.raytracing.common.Ray;

public interface Element {
    double intersect(Ray ray);

    void process(Ray ray);
}
