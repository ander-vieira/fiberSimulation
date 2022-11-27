package com.fibersim.core.raytracing.element;

import com.fibersim.core.raytracing.common.Ray;

public interface Element {
    double intersect(Ray ray, double limit);

    void process(Ray ray);
}
