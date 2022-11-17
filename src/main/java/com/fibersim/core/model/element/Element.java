package com.fibersim.core.model.element;

import com.fibersim.core.model.common.Ray;

public interface Element {
    double intersect(Ray ray);

    void process(Ray ray);
}
