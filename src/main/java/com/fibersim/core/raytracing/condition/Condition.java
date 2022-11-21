package com.fibersim.core.raytracing.condition;

import com.fibersim.core.raytracing.common.Ray;

public interface Condition {
    boolean check(Ray ray);

    Condition ALWAYS = ray -> true;
}
